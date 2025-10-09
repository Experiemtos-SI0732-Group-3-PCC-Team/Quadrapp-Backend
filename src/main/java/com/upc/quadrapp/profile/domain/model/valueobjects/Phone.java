package com.upc.quadrapp.profile.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record Phone(String phone) {

    public Phone {
        if (!isValid(phone)) {
            throw new IllegalArgumentException("Invalid phone number format");
        }
    }

    private static boolean isValid(String phone) {
        return phone != null && phone.matches("\\+?[0-9]{7,15}");
    }
}
