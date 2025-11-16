package com.upc.quadrapp.iam.application.internal.commandservices;

import com.upc.quadrapp.iam.application.internal.outboundservices.hashing.HashingService;
import com.upc.quadrapp.iam.application.internal.outboundservices.tokens.TokenService;
import com.upc.quadrapp.iam.domain.model.aggregates.User;
import com.upc.quadrapp.iam.domain.model.commands.SignInCommand;
import com.upc.quadrapp.iam.domain.model.commands.SignUpCommand;
import com.upc.quadrapp.iam.domain.model.valueobjects.Roles;
import com.upc.quadrapp.iam.domain.services.UserCommandService;
import com.upc.quadrapp.iam.infrastructure.persistence.jpa.repositories.RoleRepository;
import com.upc.quadrapp.iam.infrastructure.persistence.jpa.repositories.UserRepository;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserCommandServiceImpl implements UserCommandService {
    private final UserRepository userRepository;
    private final HashingService hashingService;
    private final TokenService tokenService;
    private final RoleRepository roleRepository;

    public UserCommandServiceImpl(UserRepository userRepository, HashingService hashingService, TokenService tokenService, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.hashingService = hashingService;
        this.tokenService = tokenService;
        this.roleRepository = roleRepository;
    }

    @Override
    public Optional<User> handle(SignUpCommand command) {
        // Validar campos requeridos
        if (command.email() == null || command.email().isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email is required");
        if (command.password() == null || command.password().isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password is required");
        if (command.firstName() == null || command.firstName().isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "First name is required");
        if (command.lastName() == null || command.lastName().isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Last name is required");
        if (!command.acceptTerms())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You must accept terms and conditions");

        // Validar si el email ya existe
        if (userRepository.existsByEmail(command.email()))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");

        // Procesar roles
        var roles = command.roles();
        if (roles.isEmpty()) {
            var role = roleRepository.findByName(Roles.ROLE_USER)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Default role not found"));
            roles = List.of(role);
        }
        roles = command.roles().stream()
                .map(role -> roleRepository.findByName(role.getName())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Role not found")))
                .toList();

        // Crear usuario con contraseña hasheada
        var user = new User(
                command.email(),
                hashingService.encode(command.password()),
                command.firstName(),
                command.lastName(),
                command.acceptTerms(),
                roles
        );

        userRepository.save(user);
        return userRepository.findByEmail(command.email());
    }

    @Override
    public Optional<ImmutablePair<User, String>> handle(SignInCommand command) {
        // Buscar usuario por email
        var user = userRepository.findByEmail(command.email());
        if (user.isEmpty())
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");

        // Validar contraseña
        if (!hashingService.matches(command.password(), user.get().getPassword()))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");

        var currentUser = user.get();

        // Actualizar lastLoginAt
        currentUser.setLastLoginAt(LocalDateTime.now());
        userRepository.save(currentUser);

        // Generar access token
        var token = tokenService.generateToken(currentUser.getEmail());

        return Optional.of(ImmutablePair.of(currentUser, token));
    }
}
