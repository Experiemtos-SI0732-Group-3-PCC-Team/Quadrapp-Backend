package com.upc.quadrapp.payment.infrastructure.persistence.jpa.repositories;

import com.upc.quadrapp.payment.domain.model.entities.ReservationPayment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReservationPaymentRepository extends JpaRepository<ReservationPayment, Long> {
    Optional<ReservationPayment> findByReservationId(Long reservationId);
}
