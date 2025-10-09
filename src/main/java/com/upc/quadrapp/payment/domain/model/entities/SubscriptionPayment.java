package com.upc.quadrapp.payment.domain.model.entities;

import com.upc.quadrapp.payment.domain.model.aggregates.Payment;
import com.upc.quadrapp.payment.domain.model.commands.CreateSubscriptionPaymentCommand;
import com.upc.quadrapp.payment.domain.model.valueobjects.PaymentStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "subscription_payments")
@Getter
@NoArgsConstructor
public class SubscriptionPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long subscriptionId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "payment_id", referencedColumnName = "id")
    private Payment payment;

    public SubscriptionPayment(Long subscriptionId, Double amount) {
        this.subscriptionId = subscriptionId;
        this.payment = new Payment(amount);
    }

    public SubscriptionPayment(CreateSubscriptionPaymentCommand command) {
        this.subscriptionId = command.subscriptionId();
        this.payment = new Payment(command.amount());
    }

    public void updateStatus(String status) {
        PaymentStatus newStatus = PaymentStatus.valueOf(status.toUpperCase());
        this.payment.setStatus(newStatus);

        if (newStatus == PaymentStatus.COMPLETED) {
            this.payment.setPaidAt(LocalDateTime.now());
        }
    }

    public void markAsPaid() {
        this.payment.markAsPaid();
    }

}
