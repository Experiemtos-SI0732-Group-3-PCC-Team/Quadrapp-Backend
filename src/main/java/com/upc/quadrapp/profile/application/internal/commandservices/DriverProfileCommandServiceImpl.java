package com.upc.quadrapp.profile.application.internal.commandservices;

import com.upc.quadrapp.profile.domain.model.aggregates.Driver;
import com.upc.quadrapp.profile.domain.model.commands.CreateDriverProfileCommand;
import com.upc.quadrapp.profile.domain.services.DriverProfileCommandService;
import com.upc.quadrapp.profile.infrastructure.persistence.jpa.repositories.DriverProfileRepository;
import org.springframework.stereotype.Service;

@Service
public class DriverProfileCommandServiceImpl implements DriverProfileCommandService {

    private final DriverProfileRepository repository;

    public DriverProfileCommandServiceImpl(DriverProfileRepository repository) {
        this.repository = repository;
    }

    @Override
    public Long createDriver(CreateDriverProfileCommand command) {
        Driver driver = new Driver(command);
        repository.save(driver);
        return driver.getId();
    }
}
