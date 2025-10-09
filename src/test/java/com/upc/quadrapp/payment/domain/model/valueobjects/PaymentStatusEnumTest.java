package com.upc.quadrapp.payment.domain.model.valueobjects;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentStatusEnumTest {
    @Test
    void shouldContainAllExpectedValues() {
        // Arrange & Act
        PaymentStatus[] statuses = PaymentStatus.values();

        // Assert
        assertEquals(5, statuses.length);
        assertTrue(java.util.EnumSet.allOf(PaymentStatus.class).contains(PaymentStatus.PENDING));
        assertTrue(java.util.EnumSet.allOf(PaymentStatus.class).contains(PaymentStatus.PROCESSING));
        assertTrue(java.util.EnumSet.allOf(PaymentStatus.class).contains(PaymentStatus.CANCELLED));
        assertTrue(java.util.EnumSet.allOf(PaymentStatus.class).contains(PaymentStatus.COMPLETED));
        assertTrue(java.util.EnumSet.allOf(PaymentStatus.class).contains(PaymentStatus.FAILED));
    }
}
