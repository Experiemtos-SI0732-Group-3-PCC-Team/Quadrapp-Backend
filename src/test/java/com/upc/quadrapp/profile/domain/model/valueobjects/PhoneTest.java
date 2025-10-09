package com.upc.quadrapp.profile.domain.model.valueobjects;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PhoneTest {

    @Test
    void shouldCreateValidPhone() {
        // Arrange & Act
        Phone phone = new Phone("+51999999999");

        // Assert
        assertEquals("+51999999999", phone.phone());
    }

    @Test
    void shouldThrowExceptionForInvalidPhone() {
        // Arrange & Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new Phone("invalid123"));
    }
}
