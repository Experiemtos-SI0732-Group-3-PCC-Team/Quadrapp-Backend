package com.upc.quadrapp.profile.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record Ruc(String ruc) {

    public Ruc {
        if (!isValid(ruc)) {
            throw new IllegalArgumentException("Invalid RUC format");
        }
    }

    private static boolean isValid(String ruc) {
        return ruc != null && ruc.matches("\\d{11}");
    }
}
