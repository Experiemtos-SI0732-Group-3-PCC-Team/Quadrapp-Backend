package com.upc.quadrapp.payment.domain.model.commands;

public record CreateSubscriptionPaymentCommand(Long subscriptionId, Double amount) {
}
