package com.upc.quadrapp.profile.domain.services;

import com.upc.quadrapp.profile.domain.model.commands.CreateParkingOwnerProfileCommand;

public interface ParkingOwnerProfileCommandService {
    Long createParkingOwner(CreateParkingOwnerProfileCommand command);
}
