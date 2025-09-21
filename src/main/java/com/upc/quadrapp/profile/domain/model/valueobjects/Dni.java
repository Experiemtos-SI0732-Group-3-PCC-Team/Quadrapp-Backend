package com.upc.quadrapp.profile.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record Dni(String dni) {

    public Dni {
        if (!isValid(dni)) {
            throw new IllegalArgumentException("Invalid DNI format");
        }
    }

    private static boolean isValid(String dni) {
        return dni != null && dni.matches("\\d{8}");
    }
}
