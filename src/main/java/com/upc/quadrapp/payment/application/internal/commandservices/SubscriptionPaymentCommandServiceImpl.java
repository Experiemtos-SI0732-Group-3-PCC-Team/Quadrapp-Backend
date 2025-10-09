package com.upc.quadrapp.payment.application.internal.commandservices;

import com.upc.quadrapp.payment.domain.model.commands.CreateSubscriptionPaymentCommand;
import com.upc.quadrapp.payment.domain.model.commands.UpdateSubscriptionPaymentCommand;
import com.upc.quadrapp.payment.domain.model.entities.SubscriptionPayment;
import com.upc.quadrapp.payment.domain.services.SubscriptionPaymentCommandService;
import com.upc.quadrapp.payment.infrastructure.persistence.jpa.repositories.SubscriptionPaymentRepository;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionPaymentCommandServiceImpl implements SubscriptionPaymentCommandService {

    private final SubscriptionPaymentRepository repository;

    public SubscriptionPaymentCommandServiceImpl(SubscriptionPaymentRepository repository) {
        this.repository = repository;
    }

    @Override
    public Long createSubscriptionPayment(CreateSubscriptionPaymentCommand command) {
        SubscriptionPayment subscriptionPayment = new SubscriptionPayment(command);
        repository.save(subscriptionPayment);
        return subscriptionPayment.getSubscriptionId();
    }

    @Override
    public void updateSubscriptionPayment(UpdateSubscriptionPaymentCommand command) {
        Long subscriptionId = command.subscriptionId();
        SubscriptionPayment subscriptionPayment = repository.findBySubscriptionId(subscriptionId).orElseThrow();
        subscriptionPayment.updateStatus(command.status());
        repository.save(subscriptionPayment);
    }
}
