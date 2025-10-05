package com.upc.quadrapp.parkingmanagement.interfaces.controllers;

import com.upc.quadrapp.parkingmanagement.application.services.ParkingApplicationService;
import com.upc.quadrapp.parkingmanagement.domain.model.commands.AddParkingSpotCommand;
import com.upc.quadrapp.parkingmanagement.domain.model.commands.CreateParkingCommand;
import com.upc.quadrapp.parkingmanagement.domain.model.commands.UpdateParkingAvailabilityCommand;
import com.upc.quadrapp.parkingmanagement.interfaces.dto.ParkingDto;
import com.upc.quadrapp.parkingmanagement.interfaces.transformers.ParkingDtoTransformer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/parkings")
public class ParkingController {

    private final ParkingApplicationService parkingService;
    private final ParkingDtoTransformer transformer;

    public ParkingController(ParkingApplicationService parkingService,
                             ParkingDtoTransformer transformer) {
        this.parkingService = parkingService;
        this.transformer = transformer;
    }

    @PostMapping
    public ResponseEntity<Long> createParking(@RequestBody ParkingDto dto) {
        CreateParkingCommand cmd = transformer.toCreateCommand(dto);
        Long id = parkingService.createParking(cmd);
        return ResponseEntity.ok(id);
    }

    @PostMapping("/{parkingId}/spots")
    public ResponseEntity<Void> addSpot(@PathVariable Long parkingId, @RequestBody Map<String, Object> payload) {
        AddParkingSpotCommand cmd = new AddParkingSpotCommand(
                parkingId,
                (Integer) payload.get("row"),
                (Integer) payload.get("column"),
                (String) payload.get("label")
        );
        parkingService.addParkingSpot(cmd);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{parkingId}/spots/{spotId}/availability")
    public ResponseEntity<Void> updateAvailability(@PathVariable Long parkingId,
                                                   @PathVariable UUID spotId,
                                                   @RequestBody Map<String, Object> payload) {
        boolean available = (Boolean) payload.get("available");
        UpdateParkingAvailabilityCommand cmd = new UpdateParkingAvailabilityCommand(parkingId, spotId, available);
        parkingService.updateParkingAvailability(cmd);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<ParkingDto>> listAll() {
        List<ParkingDto> result = parkingService.listAll().stream()
                .map(transformer::toDto)
                .toList();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParkingDto> findById(@PathVariable Long id) {
        return parkingService.findById(id)
                .map(transformer::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
