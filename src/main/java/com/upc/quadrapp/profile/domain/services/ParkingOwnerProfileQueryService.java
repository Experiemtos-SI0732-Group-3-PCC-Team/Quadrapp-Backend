package com.upc.quadrapp.profile.domain.services;

import com.upc.quadrapp.profile.domain.model.queries.GetParkingOwnerProfileByIdQuery;
import com.upc.quadrapp.profile.interfaces.rest.resources.ParkingOwnerProfileResource;

import java.util.Optional;

public interface ParkingOwnerProfileQueryService {
    Optional<ParkingOwnerProfileResource> handle(GetParkingOwnerProfileByIdQuery query);
}
