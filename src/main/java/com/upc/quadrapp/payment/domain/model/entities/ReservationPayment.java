package com.upc.quadrapp.payment.domain.model.entities;

import com.upc.quadrapp.payment.domain.model.aggregates.Payment;
import com.upc.quadrapp.payment.domain.model.commands.CreateReservationPaymentCommand;
import com.upc.quadrapp.payment.domain.model.valueobjects.PaymentStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "reservation_payments")
@Getter
@NoArgsConstructor
public class ReservationPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @NotNull
    private Long reservationId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "payment_id", referencedColumnName = "id")
    private Payment payment;

    public ReservationPayment(Long reservationId, Double amount) {
        this.reservationId = reservationId;
        this.payment = new Payment(amount);
    }

    public ReservationPayment(CreateReservationPaymentCommand command) {
        this.reservationId = command.reservationId();
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
