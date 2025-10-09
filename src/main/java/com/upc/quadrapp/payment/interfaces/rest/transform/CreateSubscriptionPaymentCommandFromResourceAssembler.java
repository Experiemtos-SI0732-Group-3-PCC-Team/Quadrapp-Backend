package com.upc.quadrapp.payment.interfaces.rest.transform;

import com.upc.quadrapp.payment.domain.model.commands.CreateSubscriptionPaymentCommand;
import com.upc.quadrapp.payment.interfaces.rest.resources.CreateSubscriptionPaymentResource;

public class CreateSubscriptionPaymentCommandFromResourceAssembler {

    public static CreateSubscriptionPaymentCommand toCommand(CreateSubscriptionPaymentResource resource) {

        return new CreateSubscriptionPaymentCommand(
                resource.subscriptionId(),
                resource.amount()
        );
    }
}
