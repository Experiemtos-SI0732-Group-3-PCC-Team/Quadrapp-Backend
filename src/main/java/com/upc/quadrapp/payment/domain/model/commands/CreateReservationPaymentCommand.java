package com.upc.quadrapp.payment.domain.model.commands;

public record CreateReservationPaymentCommand(Long reservationId, Double amount) {
}
