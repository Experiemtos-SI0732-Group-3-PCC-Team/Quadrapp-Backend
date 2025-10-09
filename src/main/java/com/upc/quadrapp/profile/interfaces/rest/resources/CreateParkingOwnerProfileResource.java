package com.upc.quadrapp.profile.interfaces.rest.resources;

public record CreateParkingOwnerProfileResource(
        String fullName,
        String city,
        String country,
        String phone,
        String companyName,
        String ruc,
        Long userId
) {
}
