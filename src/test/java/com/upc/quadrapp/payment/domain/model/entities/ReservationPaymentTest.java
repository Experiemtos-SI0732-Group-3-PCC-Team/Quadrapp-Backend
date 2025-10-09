package com.upc.quadrapp.payment.domain.model.entities;

import com.upc.quadrapp.payment.domain.model.commands.CreateReservationPaymentCommand;
import com.upc.quadrapp.payment.domain.model.valueobjects.PaymentStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ReservationPaymentTest {
    @Test
    void shouldCreateReservationPaymentFromCommand() {
        // Arrange
        CreateReservationPaymentCommand command = new CreateReservationPaymentCommand(1L, 100.0);

        // Act
        ReservationPayment payment = new ReservationPayment(command);

        // Assert
        assertEquals(1L, payment.getReservationId());
        assertNotNull(payment.getPayment());
        assertEquals(100.0, payment.getPayment().getAmount());
        assertEquals(PaymentStatus.PENDING, payment.getPayment().getStatus());
    }

    @Test
    void shouldMarkAsPaidSuccessfully() {
        // Arrange
        ReservationPayment reservationPayment = new ReservationPayment(1L, 50.0);

        // Act
        reservationPayment.markAsPaid();

        // Assert
        assertEquals(PaymentStatus.COMPLETED, reservationPayment.getPayment().getStatus());
        assertNotNull(reservationPayment.getPayment().getPaidAt());
    }

    @Test
    void shouldUpdateStatusToCompletedAndSetPaidAt() {
        // Arrange
        ReservationPayment reservationPayment = new ReservationPayment(1L, 50.0);

        // Act
        reservationPayment.updateStatus("completed");

        // Assert
        assertEquals(PaymentStatus.COMPLETED, reservationPayment.getPayment().getStatus());
        assertNotNull(reservationPayment.getPayment().getPaidAt());
    }

    @Test
    void shouldUpdateStatusToCancelledWithoutPaidAt() {
        // Arrange
        ReservationPayment reservationPayment = new ReservationPayment(1L, 50.0);

        // Act
        reservationPayment.updateStatus("cancelled");

        // Assert
        assertEquals(PaymentStatus.CANCELLED, reservationPayment.getPayment().getStatus());
        assertNull(reservationPayment.getPayment().getPaidAt());
    }
}
