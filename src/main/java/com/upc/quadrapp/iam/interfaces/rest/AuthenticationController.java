package com.upc.quadrapp.iam.interfaces.rest;

import com.upc.quadrapp.iam.application.internal.outboundservices.tokens.TokenService;
import com.upc.quadrapp.iam.domain.services.UserCommandService;
import com.upc.quadrapp.iam.interfaces.rest.resources.AuthenticatedUserResource;
import com.upc.quadrapp.iam.interfaces.rest.resources.SignInResource;
import com.upc.quadrapp.iam.interfaces.rest.resources.SignUpResource;
import com.upc.quadrapp.iam.interfaces.rest.transform.AuthenticatedUserResourceFromEntityAssembler;
import com.upc.quadrapp.iam.interfaces.rest.transform.SignInCommandFromResourceAssembler;
import com.upc.quadrapp.iam.interfaces.rest.transform.SignUpCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/authentication", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Authentication", description = "Authentication Endpoints")
public class AuthenticationController {
    private final UserCommandService userCommandService;
    private final TokenService tokenService;

    public AuthenticationController(UserCommandService userCommandService, TokenService tokenService) {
        this.userCommandService = userCommandService;
        this.tokenService = tokenService;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<AuthenticatedUserResource> signUp(@RequestBody SignUpResource resource) {
        var signUpCommand = SignUpCommandFromResourceAssembler.toCommandFromResource(resource);
        var user = userCommandService.handle(signUpCommand);
        if (user.isEmpty()) return ResponseEntity.badRequest().build();

        // Generar tokens
        var accessToken = tokenService.generateToken(user.get().getEmail());
        var refreshToken = tokenService.generateRefreshToken(user.get().getEmail());
        var expiresIn = tokenService.getExpirationTime();

        var authenticatedUserResource = AuthenticatedUserResourceFromEntityAssembler.toResourceFromEntity(
            user.get(), accessToken, refreshToken, expiresIn
        );
        return new ResponseEntity<>(authenticatedUserResource, HttpStatus.CREATED);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<AuthenticatedUserResource> signIn(@RequestBody SignInResource resource) {
        var signInCommand = SignInCommandFromResourceAssembler.toCommandFromResource(resource);
        var authenticatedUser = userCommandService.handle(signInCommand);
        if (authenticatedUser.isEmpty()) return ResponseEntity.notFound().build();

        var user = authenticatedUser.get().getLeft();
        var accessToken = authenticatedUser.get().getRight();

        // Generar refresh token
        var refreshToken = tokenService.generateRefreshToken(user.getEmail());
        var expiresIn = tokenService.getExpirationTime();

        var authenticatedUserResource = AuthenticatedUserResourceFromEntityAssembler.toResourceFromEntity(
            user, accessToken, refreshToken, expiresIn
        );
        return ResponseEntity.ok(authenticatedUserResource);
    }
}
