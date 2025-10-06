package com.upc.quadrapp.payment.domain.model.commands;

public record UpdateReservationPaymentCommand(Long reservationId,String status) {
}
