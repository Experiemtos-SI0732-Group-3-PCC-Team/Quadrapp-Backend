package com.upc.quadrapp.payment.interfaces.rest.transform;

import com.upc.quadrapp.payment.domain.model.entities.ReservationPayment;
import com.upc.quadrapp.payment.interfaces.rest.resources.ReservationPaymentResource;

public class ReservationPaymentResourceFromEntityAssembler {

    public static ReservationPaymentResource toResourceFromEntity(ReservationPayment reservationPayment) {
        return new ReservationPaymentResource(
                reservationPayment.getId(),
                reservationPayment.getReservationId(),
                reservationPayment.getPayment().getAmount(),
                reservationPayment.getPayment().getPaidAt(),
                reservationPayment.getPayment().getStatus().name()
        );
    }
}
