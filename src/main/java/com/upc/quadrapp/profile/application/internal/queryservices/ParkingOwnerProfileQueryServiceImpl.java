package com.upc.quadrapp.profile.application.internal.queryservices;

import com.upc.quadrapp.profile.domain.model.queries.GetParkingOwnerProfileByIdQuery;
import com.upc.quadrapp.profile.domain.services.ParkingOwnerProfileQueryService;
import com.upc.quadrapp.profile.infrastructure.persistence.jpa.repositories.ParkingOwnerProfileRepository;
import com.upc.quadrapp.profile.interfaces.rest.resources.ParkingOwnerProfileResource;
import com.upc.quadrapp.profile.interfaces.rest.transform.ParkingOwnerProfileResourceFromEntityAssembler;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ParkingOwnerProfileQueryServiceImpl implements ParkingOwnerProfileQueryService {

    private final ParkingOwnerProfileRepository parkingOwnerRepository;

    public ParkingOwnerProfileQueryServiceImpl(ParkingOwnerProfileRepository parkingOwnerRepository) {
        this.parkingOwnerRepository = parkingOwnerRepository;
    }

    @Override
    public Optional<ParkingOwnerProfileResource> handle(GetParkingOwnerProfileByIdQuery query) {
        return parkingOwnerRepository.findById(query.id())
                .map(ParkingOwnerProfileResourceFromEntityAssembler::toResourceFromEntity);
    }
}
