package com.upc.quadrapp.profile.interfaces.rest.transform;

import com.upc.quadrapp.profile.domain.model.commands.CreateParkingOwnerProfileCommand;
import com.upc.quadrapp.profile.domain.model.valueobjects.Phone;
import com.upc.quadrapp.profile.domain.model.valueobjects.Ruc;
import com.upc.quadrapp.profile.interfaces.rest.resources.CreateParkingOwnerProfileResource;

public class CreateParkingOwnerProfileResourceFromEntityAssembler {

    public static CreateParkingOwnerProfileCommand toCommand(CreateParkingOwnerProfileResource resource) {

        return new CreateParkingOwnerProfileCommand(
                resource.fullName(),
                resource.city(),
                resource.country(),
                new Phone(resource.phone()),
                resource.companyName(),
                new Ruc(resource.ruc()),
                resource.userId()
        );
    }
}
