package com.upc.quadrapp.payment.infrastructure.persistence.jpa.repositories;

import com.upc.quadrapp.payment.domain.model.entities.SubscriptionPayment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubscriptionPaymentRepository extends JpaRepository<SubscriptionPayment, Long> {
    Optional<SubscriptionPayment> findBySubscriptionId(Long subscriptionId);
}
