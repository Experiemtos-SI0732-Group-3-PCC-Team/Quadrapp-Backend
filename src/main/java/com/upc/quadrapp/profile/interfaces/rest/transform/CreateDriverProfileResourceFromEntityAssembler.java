package com.upc.quadrapp.profile.interfaces.rest.transform;

import com.upc.quadrapp.profile.domain.model.commands.CreateDriverProfileCommand;
import com.upc.quadrapp.profile.domain.model.valueobjects.Dni;
import com.upc.quadrapp.profile.domain.model.valueobjects.Phone;
import com.upc.quadrapp.profile.interfaces.rest.resources.CreateDriverProfileResource;

public class CreateDriverProfileResourceFromEntityAssembler {

    public static CreateDriverProfileCommand toCommand(CreateDriverProfileResource resource) {

        return new CreateDriverProfileCommand(
                resource.fullName(),
                resource.city(),
                resource.country(),
                new Phone(resource.phone()),
                new Dni(resource.dni()),
                resource.userId()
        );
    }
}
