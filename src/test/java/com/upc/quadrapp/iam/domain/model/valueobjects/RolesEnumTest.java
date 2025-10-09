package com.upc.quadrapp.iam.domain.model.valueobjects;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RolesEnumTest {

    @Test
    @DisplayName("El enum Roles debería contener todos los valores esperados")
    void shouldContainAllExpectedRoles() {
        // Arrange
        Roles[] values = Roles.values();

        // Assert
        assertTrue(List.of(values).contains(Roles.ROLE_USER));
        assertTrue(List.of(values).contains(Roles.ROLE_DRIVER));
        assertTrue(List.of(values).contains(Roles.ROLE_OWNER));
        assertTrue(List.of(values).contains(Roles.ROLE_ADMIN));
    }

    @Test
    @DisplayName("Debería permitir conversión válida de String a Enum")
    void shouldConvertStringToEnumSuccessfully() {
        // Arrange
        String validName = "ROLE_USER";

        // Act
        Roles role = Roles.valueOf(validName);

        // Assert
        assertEquals(Roles.ROLE_USER, role);
    }

    @Test
    @DisplayName("Debería lanzar excepción al convertir un String inválido a Enum")
    void shouldThrowExceptionWhenInvalidEnumName() {
        // Arrange
        String invalidName = "INVALID_ROLE";

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> Roles.valueOf(invalidName));
    }
}
