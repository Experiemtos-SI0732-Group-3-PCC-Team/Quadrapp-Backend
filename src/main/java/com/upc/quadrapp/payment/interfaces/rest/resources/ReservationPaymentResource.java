package com.upc.quadrapp.payment.interfaces.rest.resources;

import java.time.LocalDateTime;

public record ReservationPaymentResource(
        Long id,
        Long reservationId,
        Double amount,
        LocalDateTime paidAt,
        String status
) {
}
