package com.upc.quadrapp.parkingmanagement.application.services;

import com.upc.quadrapp.parkingmanagement.domain.model.aggregates.Parking;
import com.upc.quadrapp.parkingmanagement.domain.model.commands.AddParkingSpotCommand;
import com.upc.quadrapp.parkingmanagement.domain.model.commands.CreateParkingCommand;
import com.upc.quadrapp.parkingmanagement.domain.model.commands.UpdateParkingAvailabilityCommand;
import com.upc.quadrapp.parkingmanagement.domain.services.ParkingCommandService;
import com.upc.quadrapp.parkingmanagement.domain.services.ParkingQueryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ParkingApplicationService {

    private final ParkingCommandService parkingCommandService;
    private final ParkingQueryService parkingQueryService;

    public ParkingApplicationService(ParkingCommandService parkingCommandService,
                                     ParkingQueryService parkingQueryService) {
        this.parkingCommandService = parkingCommandService;
        this.parkingQueryService = parkingQueryService;
    }

    @Transactional
    public Long createParking(CreateParkingCommand cmd) {
        return parkingCommandService.handle(cmd);
    }

    @Transactional
    public void addParkingSpot(AddParkingSpotCommand cmd) {
        parkingCommandService.handle(cmd);
    }

    @Transactional
    public void updateParkingAvailability(UpdateParkingAvailabilityCommand cmd) {
        parkingCommandService.handle(cmd);
    }

    public List<Parking> listAll() {
        return parkingQueryService.findAll();
    }

    public Optional<Parking> findById(Long id) {
        return parkingQueryService.findById(id);
    }
}
