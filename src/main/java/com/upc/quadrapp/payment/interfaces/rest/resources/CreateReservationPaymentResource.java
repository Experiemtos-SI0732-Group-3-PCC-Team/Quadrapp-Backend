package com.upc.quadrapp.payment.interfaces.rest.resources;

public record CreateReservationPaymentResource(
        Long reservationId,
        Double amount
) {
}
