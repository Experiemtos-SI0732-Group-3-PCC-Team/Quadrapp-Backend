package com.upc.quadrapp.profile.interfaces.rest.transform;

import com.upc.quadrapp.profile.domain.model.aggregates.Driver;
import com.upc.quadrapp.profile.interfaces.rest.resources.DriverProfileResource;

public class DriverProfileResourceFromEntityAssembler {

    public static DriverProfileResource toResourceFromEntity(Driver driver) {
        return new DriverProfileResource(
                driver.getId(),
                driver.getFullName(),
                driver.getCity(),
                driver.getCountry(),
                driver.getPhone().phone(),
                driver.getDni().dni(),
                driver.getUserId()
        );
    }
}
