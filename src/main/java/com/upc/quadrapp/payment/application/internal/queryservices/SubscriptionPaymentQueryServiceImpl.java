package com.upc.quadrapp.payment.application.internal.queryservices;

import com.upc.quadrapp.payment.domain.model.entities.SubscriptionPayment;
import com.upc.quadrapp.payment.domain.model.queries.GetAllSubscriptionPaymentQuery;
import com.upc.quadrapp.payment.domain.model.queries.GetSubscriptionPaymentByIdQuery;
import com.upc.quadrapp.payment.domain.services.SubscriptionPaymentQueryService;
import com.upc.quadrapp.payment.infrastructure.persistence.jpa.repositories.SubscriptionPaymentRepository;
import com.upc.quadrapp.payment.interfaces.rest.resources.SubscriptionPaymentResource;
import com.upc.quadrapp.payment.interfaces.rest.transform.SubscriptionPaymentResourceFromEntityAssembler;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubscriptionPaymentQueryServiceImpl implements SubscriptionPaymentQueryService {

    private final SubscriptionPaymentRepository subscriptionPaymentRepository;

    public SubscriptionPaymentQueryServiceImpl(SubscriptionPaymentRepository subscriptionPaymentRepository) {
        this.subscriptionPaymentRepository = subscriptionPaymentRepository;
    }

    @Override
    public List<SubscriptionPaymentResource> handle(GetAllSubscriptionPaymentQuery query) {
        List<SubscriptionPayment> subscriptionPayments = subscriptionPaymentRepository.findAll();
        return subscriptionPayments.stream()
                .map(SubscriptionPaymentResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
    }

    @Override
    public Optional<SubscriptionPaymentResource> handle(GetSubscriptionPaymentByIdQuery query) {
        return subscriptionPaymentRepository.findById(query.subscriptionId())
                .map(SubscriptionPaymentResourceFromEntityAssembler::toResourceFromEntity);
    }
}
