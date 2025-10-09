package com.upc.quadrapp.profile.interfaces.rest.resources;

public record CreateDriverProfileResource(
        String fullName,
        String city,
        String country,
        String phone,
        String dni,
        Long userId
) {
}
