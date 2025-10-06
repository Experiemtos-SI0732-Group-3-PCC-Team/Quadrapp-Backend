package com.upc.quadrapp.payment.domain.services;

import com.upc.quadrapp.payment.domain.model.commands.CreateReservationPaymentCommand;
import com.upc.quadrapp.payment.domain.model.commands.UpdateReservationPaymentCommand;

public interface ReservationPaymentCommandService {
    Long createReservationPayment(CreateReservationPaymentCommand command);
    void updateReservationPayment(UpdateReservationPaymentCommand command);
}
