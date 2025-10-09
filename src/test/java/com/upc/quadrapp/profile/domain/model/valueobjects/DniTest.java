package com.upc.quadrapp.profile.domain.model.valueobjects;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DniTest {
    @Test
    void shouldCreateValidDni() {
        // Arrange & Act
        Dni dni = new Dni("87654321");

        // Assert
        assertEquals("87654321", dni.dni());
    }

    @Test
    void shouldThrowExceptionForInvalidDni() {
        // Arrange & Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new Dni("12a4567"));
    }
}
