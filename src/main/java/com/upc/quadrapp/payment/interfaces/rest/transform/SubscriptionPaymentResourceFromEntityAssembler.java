package com.upc.quadrapp.payment.interfaces.rest.transform;

import com.upc.quadrapp.payment.domain.model.entities.SubscriptionPayment;
import com.upc.quadrapp.payment.interfaces.rest.resources.SubscriptionPaymentResource;

public class SubscriptionPaymentResourceFromEntityAssembler {

    public static SubscriptionPaymentResource toResourceFromEntity(SubscriptionPayment subscriptionPayment) {
        return new SubscriptionPaymentResource(
                subscriptionPayment.getId(),
                subscriptionPayment.getSubscriptionId(),
                subscriptionPayment.getPayment().getAmount(),
                subscriptionPayment.getPayment().getPaidAt(),
                subscriptionPayment.getPayment().getStatus().name()
        );
    }
}
