package com.upc.quadrapp.payment.application.internal.queryservices;

import com.upc.quadrapp.payment.domain.model.entities.ReservationPayment;
import com.upc.quadrapp.payment.domain.model.queries.GetAllReservationPaymentQuery;
import com.upc.quadrapp.payment.domain.model.queries.GetReservationPaymentByIdQuery;
import com.upc.quadrapp.payment.domain.services.ReservationPaymentQueryService;
import com.upc.quadrapp.payment.infrastructure.persistence.jpa.repositories.ReservationPaymentRepository;
import com.upc.quadrapp.payment.interfaces.rest.resources.ReservationPaymentResource;
import com.upc.quadrapp.payment.interfaces.rest.transform.ReservationPaymentResourceFromEntityAssembler;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationPaymentQueryServiceImpl implements ReservationPaymentQueryService {

    private final ReservationPaymentRepository reservationPaymentRepository;

    public ReservationPaymentQueryServiceImpl(ReservationPaymentRepository reservationPaymentRepository) {
        this.reservationPaymentRepository = reservationPaymentRepository;
    }

    @Override
    public List<ReservationPaymentResource> handle(GetAllReservationPaymentQuery query) {
        List<ReservationPayment> reservationPayments = reservationPaymentRepository.findAll();
        return reservationPayments.stream()
                .map(ReservationPaymentResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
    }

    @Override
    public Optional<ReservationPaymentResource> handle(GetReservationPaymentByIdQuery query) {
        return reservationPaymentRepository.findById(query.reservationId())
                .map(ReservationPaymentResourceFromEntityAssembler::toResourceFromEntity);
    }
}
