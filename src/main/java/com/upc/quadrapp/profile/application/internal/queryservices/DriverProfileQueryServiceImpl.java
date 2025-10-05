package com.upc.quadrapp.profile.application.internal.queryservices;

import com.upc.quadrapp.profile.domain.model.queries.GetDriverProfileByIdQuery;
import com.upc.quadrapp.profile.domain.services.DriverProfileQueryService;
import com.upc.quadrapp.profile.infrastructure.persistence.jpa.repositories.DriverProfileRepository;
import com.upc.quadrapp.profile.interfaces.rest.resources.DriverProfileResource;
import com.upc.quadrapp.profile.interfaces.rest.transform.DriverProfileResourceFromEntityAssembler;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DriverProfileQueryServiceImpl implements DriverProfileQueryService {

    private final DriverProfileRepository driverRepository;

    public DriverProfileQueryServiceImpl(DriverProfileRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    @Override
    public Optional<DriverProfileResource> handle(GetDriverProfileByIdQuery query) {
        return driverRepository.findById(query.id())
                .map(DriverProfileResourceFromEntityAssembler::toResourceFromEntity);
    }
}
