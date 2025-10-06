package com.upc.quadrapp.payment.domain.services;

import com.upc.quadrapp.payment.domain.model.queries.GetAllSubscriptionPaymentQuery;
import com.upc.quadrapp.payment.domain.model.queries.GetSubscriptionPaymentByIdQuery;
import com.upc.quadrapp.payment.interfaces.rest.resources.SubscriptionPaymentResource;

import java.util.List;
import java.util.Optional;

public interface SubscriptionPaymentQueryService {
    List<SubscriptionPaymentResource> handle(GetAllSubscriptionPaymentQuery query);
    Optional<SubscriptionPaymentResource> handle(GetSubscriptionPaymentByIdQuery query);
}
