package com.upc.quadrapp.payment.interfaces.rest.transform;

import com.upc.quadrapp.payment.domain.model.commands.CreateReservationPaymentCommand;
import com.upc.quadrapp.payment.interfaces.rest.resources.CreateReservationPaymentResource;

public class CreateReservationPaymentCommandFromResourceAssembler {

    public static CreateReservationPaymentCommand toCommand(CreateReservationPaymentResource resource) {

        return new CreateReservationPaymentCommand(
                resource.reservationId(),
                resource.amount()
        );
    }
}
