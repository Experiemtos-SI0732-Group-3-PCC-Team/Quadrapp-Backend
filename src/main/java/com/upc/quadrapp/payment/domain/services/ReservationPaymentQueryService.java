package com.upc.quadrapp.payment.domain.services;

import com.upc.quadrapp.payment.domain.model.queries.GetAllReservationPaymentQuery;
import com.upc.quadrapp.payment.domain.model.queries.GetReservationPaymentByIdQuery;
import com.upc.quadrapp.payment.interfaces.rest.resources.ReservationPaymentResource;

import java.util.List;
import java.util.Optional;

public interface ReservationPaymentQueryService {
    List<ReservationPaymentResource> handle(GetAllReservationPaymentQuery query);
    Optional<ReservationPaymentResource> handle(GetReservationPaymentByIdQuery query);
}
