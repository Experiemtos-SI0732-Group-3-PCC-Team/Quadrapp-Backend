package com.upc.quadrapp.payment.application.internal.commandservices;

import com.upc.quadrapp.payment.domain.model.commands.CreateReservationPaymentCommand;
import com.upc.quadrapp.payment.domain.model.commands.UpdateReservationPaymentCommand;
import com.upc.quadrapp.payment.domain.model.commands.UpdateSubscriptionPaymentCommand;
import com.upc.quadrapp.payment.domain.model.entities.ReservationPayment;
import com.upc.quadrapp.payment.domain.model.entities.SubscriptionPayment;
import com.upc.quadrapp.payment.domain.services.ReservationPaymentCommandService;
import com.upc.quadrapp.payment.infrastructure.persistence.jpa.repositories.ReservationPaymentRepository;
import org.springframework.stereotype.Service;

@Service
public class ReservationPaymentCommandServiceImpl implements ReservationPaymentCommandService {

    private final ReservationPaymentRepository repository;

    public ReservationPaymentCommandServiceImpl(ReservationPaymentRepository repository) {
        this.repository = repository;
    }

    @Override
    public Long createReservationPayment(CreateReservationPaymentCommand command) {
        ReservationPayment reservationPayment = new ReservationPayment(command);
        repository.save(reservationPayment);
        return reservationPayment.getReservationId();
    }

    @Override
    public void updateReservationPayment(UpdateReservationPaymentCommand command) {
        Long reservationId = command.reservationId();
        ReservationPayment reservationPayment = repository.findByReservationId(reservationId).orElseThrow();
        reservationPayment.updateStatus(command.status());
        repository.save(reservationPayment);
    }
}
