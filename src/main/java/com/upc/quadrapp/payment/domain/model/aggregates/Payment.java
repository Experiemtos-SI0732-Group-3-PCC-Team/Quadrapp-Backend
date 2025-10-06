package com.upc.quadrapp.payment.domain.model.aggregates;

import com.upc.quadrapp.payment.domain.model.entities.ReservationPayment;
import com.upc.quadrapp.payment.domain.model.entities.SubscriptionPayment;
import com.upc.quadrapp.payment.domain.model.valueobjects.PaymentStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Setter
@Getter
@NoArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    private Double amount;

    @NotNull
    @NotBlank
    private LocalDateTime paidAt;

    @NotNull
    @NotBlank
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @OneToOne(mappedBy = "payment", fetch = FetchType.LAZY)
    private ReservationPayment reservationPayment;

    @OneToOne(mappedBy = "payment", fetch = FetchType.LAZY)
    private SubscriptionPayment subscriptionPayment;


    public Payment(Double amount) {
        this.amount = amount;
        this.status = PaymentStatus.PENDING;
    }

    public void markAsPaid() {
        this.status = PaymentStatus.COMPLETED;
        this.paidAt = LocalDateTime.now();
    }

    public boolean isForSubscription() {
        return reservationPayment != null;
    }

    public boolean isForReservation() {
        return subscriptionPayment != null;
    }

}
