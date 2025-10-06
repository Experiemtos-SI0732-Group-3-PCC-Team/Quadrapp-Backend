package com.upc.quadrapp.payment.interfaces.rest.transform;

import com.upc.quadrapp.payment.domain.model.commands.UpdateSubscriptionPaymentCommand;
import com.upc.quadrapp.payment.interfaces.rest.resources.UpdateSubscriptionPaymentResource;

public class UpdateSubscriptionPaymentCommandFromResourceAssembler {

    public static UpdateSubscriptionPaymentCommand toCommand(Long subscriptionId, UpdateSubscriptionPaymentResource resource) {
        return new UpdateSubscriptionPaymentCommand(
                subscriptionId,
                resource.status()
        );
    }
}
