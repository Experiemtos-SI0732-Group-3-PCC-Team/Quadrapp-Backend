package com.upc.quadrapp.payment.interfaces.rest.transform;

import com.upc.quadrapp.payment.domain.model.commands.UpdateReservationPaymentCommand;
import com.upc.quadrapp.payment.interfaces.rest.resources.UpdateReservationPaymentResource;

public class UpdateReservationPaymentCommandFromResourceAssembler {

    public static UpdateReservationPaymentCommand toCommand(Long reservationId, UpdateReservationPaymentResource resource) {
        return new UpdateReservationPaymentCommand(
                reservationId,
                resource.status()
        );
    }
}
