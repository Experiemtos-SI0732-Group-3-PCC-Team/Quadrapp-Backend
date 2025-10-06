package com.upc.quadrapp.payment.interfaces.rest;

import com.upc.quadrapp.payment.application.internal.commandservices.SubscriptionPaymentCommandServiceImpl;
import com.upc.quadrapp.payment.application.internal.queryservices.SubscriptionPaymentQueryServiceImpl;
import com.upc.quadrapp.payment.domain.model.queries.GetAllSubscriptionPaymentQuery;
import com.upc.quadrapp.payment.domain.model.queries.GetSubscriptionPaymentByIdQuery;
import com.upc.quadrapp.payment.interfaces.rest.resources.CreateSubscriptionPaymentResource;
import com.upc.quadrapp.payment.interfaces.rest.resources.SubscriptionPaymentResource;
import com.upc.quadrapp.payment.interfaces.rest.resources.UpdateSubscriptionPaymentResource;
import com.upc.quadrapp.payment.interfaces.rest.transform.CreateSubscriptionPaymentCommandFromResourceAssembler;
import com.upc.quadrapp.payment.interfaces.rest.transform.UpdateSubscriptionPaymentCommandFromResourceAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/subscription-payments")
public class SubscriptionPaymentController {

    private final SubscriptionPaymentCommandServiceImpl commandService;
    private final SubscriptionPaymentQueryServiceImpl queryService;

    public SubscriptionPaymentController(SubscriptionPaymentCommandServiceImpl commandService, SubscriptionPaymentQueryServiceImpl queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @GetMapping
    public ResponseEntity<List<SubscriptionPaymentResource>> getAllSubscriptionPayments() {
        var resources = queryService.handle(new GetAllSubscriptionPaymentQuery());
        return ResponseEntity.ok(resources);
    }


    @GetMapping("/{subscriptionId}")
    public ResponseEntity<SubscriptionPaymentResource> getSubscriptionPaymentById(@PathVariable Long subscriptionId) {
        var result = queryService.handle(new GetSubscriptionPaymentByIdQuery(subscriptionId));
        return result.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Long> createSubscriptionPayment(@RequestBody CreateSubscriptionPaymentResource resource) {
        var command = CreateSubscriptionPaymentCommandFromResourceAssembler.toCommand(resource);
        Long id = commandService.createSubscriptionPayment(command);
        return ResponseEntity.ok(id);
    }

    @PutMapping("/{subscriptionId}/status")
    public ResponseEntity<?> updateSubscriptionPaymentStatus(@PathVariable Long subscriptionId, @RequestBody UpdateSubscriptionPaymentResource resource) {
        var command = UpdateSubscriptionPaymentCommandFromResourceAssembler.toCommand(subscriptionId, resource);
        commandService.updateSubscriptionPayment(command);
        return ResponseEntity.ok().build();
    }
}
