package com.upc.quadrapp.iam.domain.model.entities;

import com.upc.quadrapp.iam.domain.model.valueobjects.Roles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class RoleTest {

    @Test
    @DisplayName("Debería crear un rol con el enum correcto")
    void shouldCreateRoleWithValidEnum() {
        // Arrange
        Roles expectedRole = Roles.ROLE_ADMIN;

        // Act
        Role role = new Role(expectedRole);

        // Assert
        assertEquals(expectedRole, role.getName());
    }

    @Test
    @DisplayName("Debería crear un rol a partir del nombre en texto")
    void shouldCreateRoleFromStringName() {
        // Arrange
        String roleName = "ROLE_DRIVER";

        // Act
        Role role = Role.toRoleFromName(roleName);

        // Assert
        assertEquals(Roles.ROLE_DRIVER, role.getName());
    }

    @Test
    @DisplayName("Debería obtener el rol por defecto ROLE_USER")
    void shouldReturnDefaultRole() {
        // Act
        Role defaultRole = Role.getDefaultRole();

        // Assert
        assertEquals(Roles.ROLE_USER, defaultRole.getName());
    }

    @Test
    @DisplayName("Debería devolver ROLE_USER si la lista de roles es nula o vacía")
    void shouldReturnDefaultRoleWhenRoleListIsNullOrEmpty() {
        // Arrange
        List<Role> nullRoles = null;
        List<Role> emptyRoles = List.of();

        // Act
        List<Role> resultFromNull = Role.validateRoleSet(nullRoles);
        List<Role> resultFromEmpty = Role.validateRoleSet(emptyRoles);

        // Assert
        assertEquals(1, resultFromNull.size());
        assertEquals(Roles.ROLE_USER, resultFromNull.get(0).getName());

        assertEquals(1, resultFromEmpty.size());
        assertEquals(Roles.ROLE_USER, resultFromEmpty.get(0).getName());
    }

    @Test
    @DisplayName("Debería conservar los roles si la lista contiene elementos válidos")
    void shouldKeepRolesWhenListIsValid() {
        // Arrange
        List<Role> roles = List.of(new Role(Roles.ROLE_OWNER));

        // Act
        List<Role> validatedRoles = Role.validateRoleSet(roles);

        // Assert
        assertEquals(1, validatedRoles.size());
        assertEquals(Roles.ROLE_OWNER, validatedRoles.get(0).getName());
    }
}
