package com.upc.quadrapp.profile.application.internal.commandservices;

import com.upc.quadrapp.profile.domain.model.aggregates.ParkingOwner;
import com.upc.quadrapp.profile.domain.model.commands.CreateParkingOwnerProfileCommand;
import com.upc.quadrapp.profile.domain.services.ParkingOwnerProfileCommandService;
import com.upc.quadrapp.profile.infrastructure.persistence.jpa.repositories.ParkingOwnerProfileRepository;
import org.springframework.stereotype.Service;

@Service
public class ParkingOwnerProfileCommandServiceImpl implements ParkingOwnerProfileCommandService {

    private final ParkingOwnerProfileRepository repository;

    public ParkingOwnerProfileCommandServiceImpl(ParkingOwnerProfileRepository repository) {
        this.repository = repository;
    }

    @Override
    public Long createParkingOwner(CreateParkingOwnerProfileCommand command) {
        ParkingOwner parkingOwner = new ParkingOwner(command);
        repository.save(parkingOwner);
        return parkingOwner.getId();
    }
}
