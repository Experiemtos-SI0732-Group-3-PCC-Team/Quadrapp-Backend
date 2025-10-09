package com.upc.quadrapp.payment.domain.services;

import com.upc.quadrapp.payment.domain.model.commands.CreateSubscriptionPaymentCommand;
import com.upc.quadrapp.payment.domain.model.commands.UpdateSubscriptionPaymentCommand;

public interface SubscriptionPaymentCommandService {
    Long createSubscriptionPayment(CreateSubscriptionPaymentCommand command);
    void updateSubscriptionPayment(UpdateSubscriptionPaymentCommand command);
}
