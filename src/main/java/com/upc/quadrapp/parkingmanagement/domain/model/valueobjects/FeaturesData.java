package com.upc.quadrapp.parkingmanagement.domain.model.valueobjects;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FeaturesData {
    @Embedded
    private Security security;
    @Embedded
    private Amenities amenities;
    @Embedded
    private Services services;
    @Embedded
    private Payments payments;

    @Embeddable
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Security {
        private Boolean security24h;
        private Boolean cameras;
        private Boolean lighting;
        private Boolean accessControl;
    }

    @Embeddable
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Amenities {
        private Boolean covered;
        private Boolean elevator;
        private Boolean bathrooms;
        private Boolean carWash;
    }

    @Embeddable
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Services {
        private Boolean electricCharging;
        private Boolean freeWifi;
        private Boolean valetService;
        private Boolean maintenance;
    }

    @Embeddable
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Payments {
        private Boolean cardPayment;
        private Boolean mobilePayment;
        private Boolean monthlyPasses;
        private Boolean corporateRates;
    }
}
