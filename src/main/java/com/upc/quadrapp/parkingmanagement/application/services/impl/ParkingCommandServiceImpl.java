package com.upc.quadrapp.parkingmanagement.application.services.impl;

import com.upc.quadrapp.parkingmanagement.domain.events.ParkingCreatedEvent;
import com.upc.quadrapp.parkingmanagement.domain.model.aggregates.Parking;
import com.upc.quadrapp.parkingmanagement.domain.model.commands.AddParkingSpotCommand;
import com.upc.quadrapp.parkingmanagement.domain.model.commands.CreateParkingCommand;
import com.upc.quadrapp.parkingmanagement.domain.model.commands.UpdateParkingAvailabilityCommand;
import com.upc.quadrapp.parkingmanagement.domain.services.ParkingCommandService;
import com.upc.quadrapp.parkingmanagement.infrastructure.persistence.repositories.ParkingRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ParkingCommandServiceImpl implements ParkingCommandService {

    private final ParkingRepository parkingRepository;
    private final ApplicationEventPublisher eventPublisher;

    public ParkingCommandServiceImpl(ParkingRepository parkingRepository,
                                     ApplicationEventPublisher eventPublisher) {
        this.parkingRepository = parkingRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    @Transactional
    public Long handle(CreateParkingCommand command) {
        Parking parking = new Parking(command);
        Parking saved = parkingRepository.save(parking);
        eventPublisher.publishEvent(new ParkingCreatedEvent(saved.getId(), saved.getOwnerId()));
        return saved.getId();
    }

    @Override
    @Transactional
    public void handle(AddParkingSpotCommand command) {
        Parking p = parkingRepository.findById(command.parkingId())
                .orElseThrow(() -> new IllegalArgumentException("Parking not found"));
        p.addParkingSpot(command);
        parkingRepository.save(p);
    }

    @Override
    @Transactional
    public void handle(UpdateParkingAvailabilityCommand command) {
        Parking p = parkingRepository.findById(command.parkingId())
                .orElseThrow(() -> new IllegalArgumentException("Parking not found"));
        p.updateParkingAvailability(command);
        parkingRepository.save(p);
    }
}
