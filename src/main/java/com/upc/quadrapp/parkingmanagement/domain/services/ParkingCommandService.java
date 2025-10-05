package com.upc.quadrapp.parkingmanagement.domain.services;

import com.upc.quadrapp.parkingmanagement.domain.model.commands.AddParkingSpotCommand;
import com.upc.quadrapp.parkingmanagement.domain.model.commands.CreateParkingCommand;
import com.upc.quadrapp.parkingmanagement.domain.model.commands.UpdateParkingAvailabilityCommand;

public interface ParkingCommandService {
    Long handle(CreateParkingCommand command);
    void handle(AddParkingSpotCommand command);
    void handle(UpdateParkingAvailabilityCommand command);
}
