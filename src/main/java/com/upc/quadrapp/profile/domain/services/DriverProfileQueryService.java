package com.upc.quadrapp.profile.domain.services;

import com.upc.quadrapp.profile.domain.model.queries.GetDriverProfileByIdQuery;
import com.upc.quadrapp.profile.interfaces.rest.resources.DriverProfileResource;

import java.util.Optional;

public interface DriverProfileQueryService {
    Optional<DriverProfileResource> handle(GetDriverProfileByIdQuery query);
}
