package com.upc.quadrapp.payment.interfaces.rest.resources;

import java.time.LocalDateTime;

public record SubscriptionPaymentResource(
        Long id,
        Long subscriptionId,
        Double amount,
        LocalDateTime paidAt,
        String status
) {
}
