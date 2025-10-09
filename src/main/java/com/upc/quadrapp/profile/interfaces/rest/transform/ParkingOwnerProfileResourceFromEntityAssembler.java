package com.upc.quadrapp.profile.interfaces.rest.transform;

import com.upc.quadrapp.profile.domain.model.aggregates.ParkingOwner;
import com.upc.quadrapp.profile.interfaces.rest.resources.ParkingOwnerProfileResource;

public class ParkingOwnerProfileResourceFromEntityAssembler {

    public static ParkingOwnerProfileResource toResourceFromEntity(ParkingOwner parkingOwner) {
        return new ParkingOwnerProfileResource(
                parkingOwner.getId(),
                parkingOwner.getFullName(),
                parkingOwner.getCity(),
                parkingOwner.getCountry(),
                parkingOwner.getPhone().phone(),
                parkingOwner.getCompanyName(),
                parkingOwner.getRuc().ruc(),
                parkingOwner.getUserId()
        );
    }
}
