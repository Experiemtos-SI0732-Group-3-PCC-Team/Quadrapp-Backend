package com.upc.quadrapp.parkingmanagement.domain.model.valueobjects;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PricingData {
    @Min(value = 0, message = "Hourly rate must be non-negative")
    private Double hourlyRate;

    @Min(value = 0, message = "Daily rate must be non-negative")
    private Double dailyRate;

    @Min(value = 0, message = "Monthly rate must be non-negative")
    private Double monthlyRate;

    private String currency;
    private String minimumStay;
    private Boolean open24h;

    @Embedded
    private OperatingHours operatingHours;

    @Embedded
    private OperatingDays operatingDays;

    @Embedded
    private Promotions promotions;

    @Embeddable
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OperatingHours {
        private String openTime;
        private String closeTime;
    }

    @Embeddable
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OperatingDays {
        private Boolean monday;
        private Boolean tuesday;
        private Boolean wednesday;
        private Boolean thursday;
        private Boolean friday;
        private Boolean saturday;
        private Boolean sunday;
    }

    @Embeddable
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Promotions {
        private Boolean earlyBird;
        private Boolean weekend;
        private Boolean longStay;
    }
}
