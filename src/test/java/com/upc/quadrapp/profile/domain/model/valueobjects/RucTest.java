package com.upc.quadrapp.profile.domain.model.valueobjects;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RucTest {

    @Test
    void shouldCreateValidRuc() {
        // Arrange & Act
        Ruc ruc = new Ruc("20481234567");

        // Assert
        assertEquals("20481234567", ruc.ruc());
    }

    @Test
    void shouldThrowExceptionForInvalidRuc() {
        // Arrange & Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new Ruc("ABC123"));
    }
}