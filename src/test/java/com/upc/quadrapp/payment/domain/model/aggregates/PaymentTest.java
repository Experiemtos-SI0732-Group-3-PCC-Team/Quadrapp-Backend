package com.upc.quadrapp.payment.domain.model.aggregates;

import com.upc.quadrapp.payment.domain.model.entities.ReservationPayment;
import com.upc.quadrapp.payment.domain.model.entities.SubscriptionPayment;
import com.upc.quadrapp.payment.domain.model.valueobjects.PaymentStatus;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PaymentTest {

    @Test
    void shouldInitializePaymentWithPendingStatus() {
        // Arrange
        Double amount = 100.0;

        // Act
        Payment payment = new Payment(amount);

        // Assert
        assertEquals(PaymentStatus.PENDING, payment.getStatus());
        assertEquals(amount, payment.getAmount());
        assertNull(payment.getPaidAt());
    }

    @Test
    void shouldMarkPaymentAsPaid() {
        // Arrange
        Payment payment = new Payment(50.0);

        // Act
        payment.markAsPaid();

        // Assert
        assertEquals(PaymentStatus.COMPLETED, payment.getStatus());
        assertNotNull(payment.getPaidAt());
    }

    @Test
    void shouldRecognizePaymentForReservation() {
        // Arrange
        Payment payment = new Payment(20.0);
        payment.setReservationPayment(new ReservationPayment(1L, 20.0));

        // Act
        boolean result = payment.isForReservation();

        // Assert
        assertTrue(result);
        assertFalse(payment.isForSubscription());
    }

    @Test
    void shouldRecognizePaymentForSubscription() {
        // Arrange
        Payment payment = new Payment(20.0);
        payment.setSubscriptionPayment(new SubscriptionPayment(1L, 20.0));

        // Act
        boolean result = payment.isForSubscription();

        // Assert
        assertTrue(result);
        assertFalse(payment.isForReservation());
    }
}
