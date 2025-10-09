package com.upc.quadrapp.iam.domain.model.aggregates;

import com.upc.quadrapp.iam.domain.model.entities.Role;
import com.upc.quadrapp.iam.domain.model.valueobjects.Roles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


class UserTest {

    @Test
    @DisplayName("Debería agregar un rol único al usuario")
    void shouldAddSingleRole_whenValidRoleProvided() {
        // Arrange
        User user = new User("leonardo", "123456");
        Role adminRole = new Role(Roles.ROLE_ADMIN);

        // Act
        user.addRole(adminRole);

        // Assert
        assertEquals(1, user.getRoles().size());
        assertTrue(user.getRoles().stream()
                .anyMatch(r -> r.getName() == Roles.ROLE_ADMIN));
    }

    @Test
    @DisplayName("Debería agregar múltiples roles al usuario")
    void shouldAddMultipleRoles_whenListProvided() {
        // Arrange
        User user = new User("martin", "password123");
        List<Role> roles = List.of(
                new Role(Roles.ROLE_USER),
                new Role(Roles.ROLE_DRIVER)
        );

        // Act
        user.addRoles(roles);

        // Assert
        Set<Role> userRoles = user.getRoles();
        assertEquals(2, userRoles.size());
        assertTrue(userRoles.stream().anyMatch(r -> r.getName() == Roles.ROLE_USER));
        assertTrue(userRoles.stream().anyMatch(r -> r.getName() == Roles.ROLE_DRIVER));
    }

    @Test
    @DisplayName("Debería asignar el rol por defecto ROLE_USER si la lista está vacía")
    void shouldAssignDefaultRole_whenEmptyListProvided() {
        // Arrange
        User user = new User("andrea", "securepass");

        // Act
        user.addRoles(List.of());

        // Assert
        assertEquals(1, user.getRoles().size());
        assertTrue(user.getRoles().stream()
                .anyMatch(r -> r.getName() == Roles.ROLE_USER));
    }

    @Test
    @DisplayName("Debería asignar roles correctamente usando el constructor con lista")
    void shouldAssignRoles_whenUsingConstructorWithList() {
        // Arrange
        List<Role> initialRoles = List.of(
                new Role(Roles.ROLE_DRIVER),
                new Role(Roles.ROLE_OWNER)
        );

        // Act
        User user = new User("sofia", "pass", initialRoles);

        // Assert
        assertEquals(2, user.getRoles().size());
        assertTrue(user.getRoles().stream().anyMatch(r -> r.getName() == Roles.ROLE_DRIVER));
        assertTrue(user.getRoles().stream().anyMatch(r -> r.getName() == Roles.ROLE_OWNER));
    }

    @Test
    @DisplayName("Debería asignar el rol por defecto cuando la lista inicial es nula")
    void shouldAssignDefaultRole_whenConstructorListIsNull() {
        // Act
        User user = new User("jorge", "clave", null);

        // Assert
        assertEquals(1, user.getRoles().size());
        assertTrue(user.getRoles().stream()
                .anyMatch(r -> r.getName() == Roles.ROLE_USER));
    }
}
