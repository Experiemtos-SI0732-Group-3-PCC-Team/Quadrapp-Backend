package com.upc.quadrapp.payment.interfaces.rest;

import com.upc.quadrapp.payment.application.internal.commandservices.ReservationPaymentCommandServiceImpl;
import com.upc.quadrapp.payment.application.internal.queryservices.ReservationPaymentQueryServiceImpl;
import com.upc.quadrapp.payment.domain.model.queries.GetAllReservationPaymentQuery;
import com.upc.quadrapp.payment.domain.model.queries.GetReservationPaymentByIdQuery;
import com.upc.quadrapp.payment.interfaces.rest.resources.CreateReservationPaymentResource;
import com.upc.quadrapp.payment.interfaces.rest.resources.UpdateReservationPaymentResource;
import com.upc.quadrapp.payment.interfaces.rest.transform.CreateReservationPaymentCommandFromResourceAssembler;
import com.upc.quadrapp.payment.interfaces.rest.transform.UpdateReservationPaymentCommandFromResourceAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/reservation-payments")
public class ReservationPaymentController {

    private final ReservationPaymentCommandServiceImpl commandService;
    private final ReservationPaymentQueryServiceImpl queryService;

    public ReservationPaymentController(ReservationPaymentCommandServiceImpl commandService, ReservationPaymentQueryServiceImpl queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @GetMapping
    public ResponseEntity<?> getAllReservationPayments() {
        var reservationPayments = queryService.handle(new GetAllReservationPaymentQuery());
        return ResponseEntity.ok(reservationPayments);
    }

    @GetMapping("/{reservationId}")
    public ResponseEntity<?> getReservationPaymentById(@PathVariable Long reservationId) {
        var result = queryService.handle(new GetReservationPaymentByIdQuery(reservationId));
        return result.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createReservationPayment(@RequestBody CreateReservationPaymentResource resource) {
        var command = CreateReservationPaymentCommandFromResourceAssembler.toCommand(resource);
        Long reservationId = commandService.createReservationPayment(command);
        return ResponseEntity.ok(reservationId);
    }

    @PutMapping("/{reservationId}/status")
    public ResponseEntity<?> updateReservationPaymentStatus(@PathVariable Long reservationId, @RequestBody UpdateReservationPaymentResource resource) {
        var command = UpdateReservationPaymentCommandFromResourceAssembler.toCommand(reservationId, resource);
        commandService.updateReservationPayment(command);
        return ResponseEntity.ok().build();
    }
}
