package com.upc.quadrapp.payment.interfaces.rest.resources;

public record CreateSubscriptionPaymentResource(
        Long subscriptionId,
        Double amount
) {
}
