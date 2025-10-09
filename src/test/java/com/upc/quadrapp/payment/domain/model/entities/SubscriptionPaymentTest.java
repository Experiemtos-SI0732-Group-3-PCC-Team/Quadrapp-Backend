package com.upc.quadrapp.payment.domain.model.entities;

import com.upc.quadrapp.payment.domain.model.commands.CreateSubscriptionPaymentCommand;
import com.upc.quadrapp.payment.domain.model.valueobjects.PaymentStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SubscriptionPaymentTest {
    @Test
    void shouldCreateSubscriptionPaymentFromCommand() {
        // Arrange
        CreateSubscriptionPaymentCommand command = new CreateSubscriptionPaymentCommand(5L, 200.0);

        // Act
        SubscriptionPayment subscriptionPayment = new SubscriptionPayment(command);

        // Assert
        assertEquals(5L, subscriptionPayment.getSubscriptionId());
        assertEquals(200.0, subscriptionPayment.getPayment().getAmount());
        assertEquals(PaymentStatus.PENDING, subscriptionPayment.getPayment().getStatus());
    }

    @Test
    void shouldMarkSubscriptionAsPaid() {
        // Arrange
        SubscriptionPayment subscriptionPayment = new SubscriptionPayment(2L, 75.0);

        // Act
        subscriptionPayment.markAsPaid();

        // Assert
        assertEquals(PaymentStatus.COMPLETED, subscriptionPayment.getPayment().getStatus());
        assertNotNull(subscriptionPayment.getPayment().getPaidAt());
    }

    @Test
    void shouldUpdateStatusToProcessing() {
        // Arrange
        SubscriptionPayment subscriptionPayment = new SubscriptionPayment(2L, 75.0);

        // Act
        subscriptionPayment.updateStatus("processing");

        // Assert
        assertEquals(PaymentStatus.PROCESSING, subscriptionPayment.getPayment().getStatus());
        assertNull(subscriptionPayment.getPayment().getPaidAt());
    }
}
