package com.upc.quadrapp.profile.domain.services;

import com.upc.quadrapp.profile.domain.model.commands.CreateDriverProfileCommand;

public interface DriverProfileCommandService {
    Long createDriver(CreateDriverProfileCommand command);
}
