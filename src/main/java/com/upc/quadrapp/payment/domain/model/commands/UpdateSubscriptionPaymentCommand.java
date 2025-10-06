package com.upc.quadrapp.payment.domain.model.commands;

public record UpdateSubscriptionPaymentCommand(Long subscriptionId, String status) {
}
