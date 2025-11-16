package com.upc.quadrapp.parkingmanagement.domain.model.aggregates;

import com.upc.quadrapp.parkingmanagement.domain.model.valueobjects.*;
import com.upc.quadrapp.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "parking_profiles", indexes = {
    @Index(name = "idx_owner_id", columnList = "ownerId"),
    @Index(name = "idx_status", columnList = "status")
})
@Getter
@Setter
public class ParkingProfile extends AuditableAbstractAggregateRoot<ParkingProfile> {

    @NotBlank(message = "Name is required")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Owner ID is required")
    @Column(nullable = false)
    private String ownerId;

    @NotNull(message = "Type is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ParkingType type;

    @NotNull(message = "Status is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ParkingStatus status;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "addressLine", column = @Column(name = "location_address_line", length = 500)),
        @AttributeOverride(name = "city", column = @Column(name = "location_city", length = 100)),
        @AttributeOverride(name = "postalCode", column = @Column(name = "location_postal_code", length = 20)),
        @AttributeOverride(name = "state", column = @Column(name = "location_state", length = 100)),
        @AttributeOverride(name = "country", column = @Column(name = "location_country", length = 100)),
        @AttributeOverride(name = "latitude", column = @Column(name = "location_latitude")),
        @AttributeOverride(name = "longitude", column = @Column(name = "location_longitude"))
    })
    private LocationData location;

    @NotNull(message = "Total spaces is required")
    @Column(nullable = false)
    private Integer totalSpaces;

    @NotNull(message = "Accessible spaces is required")
    @Column(nullable = false)
    private Integer accessibleSpaces;

    private Integer occupiedSpaces;

    @NotBlank(message = "Phone is required")
    @Column(nullable = false)
    private String phone;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    @Column(nullable = false)
    private String email;

    private String website;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "hourlyRate", column = @Column(name = "pricing_hourly_rate")),
        @AttributeOverride(name = "dailyRate", column = @Column(name = "pricing_daily_rate")),
        @AttributeOverride(name = "monthlyRate", column = @Column(name = "pricing_monthly_rate")),
        @AttributeOverride(name = "currency", column = @Column(name = "pricing_currency", length = 10)),
        @AttributeOverride(name = "minimumStay", column = @Column(name = "pricing_minimum_stay", length = 50)),
        @AttributeOverride(name = "open24h", column = @Column(name = "pricing_open_24h")),
        @AttributeOverride(name = "operatingHoursOpenTime", column = @Column(name = "pricing_operating_open_time")),
        @AttributeOverride(name = "operatingHoursCloseTime", column = @Column(name = "pricing_operating_close_time")),
        @AttributeOverride(name = "operatingDaysMonday", column = @Column(name = "pricing_operating_monday")),
        @AttributeOverride(name = "operatingDaysTuesday", column = @Column(name = "pricing_operating_tuesday")),
        @AttributeOverride(name = "operatingDaysWednesday", column = @Column(name = "pricing_operating_wednesday")),
        @AttributeOverride(name = "operatingDaysThursday", column = @Column(name = "pricing_operating_thursday")),
        @AttributeOverride(name = "operatingDaysFriday", column = @Column(name = "pricing_operating_friday")),
        @AttributeOverride(name = "operatingDaysSaturday", column = @Column(name = "pricing_operating_saturday")),
        @AttributeOverride(name = "operatingDaysSunday", column = @Column(name = "pricing_operating_sunday")),
        @AttributeOverride(name = "promotionsEarlyBird", column = @Column(name = "pricing_promo_early_bird")),
        @AttributeOverride(name = "promotionsWeekend", column = @Column(name = "pricing_promo_weekend")),
        @AttributeOverride(name = "promotionsLongStay", column = @Column(name = "pricing_promo_long_stay"))
    })
    private PricingData pricing;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "securitySecurity24h", column = @Column(name = "features_security_24h")),
        @AttributeOverride(name = "securityCameras", column = @Column(name = "features_security_cameras")),
        @AttributeOverride(name = "securityLighting", column = @Column(name = "features_security_lighting")),
        @AttributeOverride(name = "securityAccessControl", column = @Column(name = "features_security_access_control")),
        @AttributeOverride(name = "amenitiesCovered", column = @Column(name = "features_amenity_covered")),
        @AttributeOverride(name = "amenitiesElevator", column = @Column(name = "features_amenity_elevator")),
        @AttributeOverride(name = "amenitiesBathrooms", column = @Column(name = "features_amenity_bathrooms")),
        @AttributeOverride(name = "amenitiesCarWash", column = @Column(name = "features_amenity_car_wash")),
        @AttributeOverride(name = "servicesElectricCharging", column = @Column(name = "features_service_electric_charging")),
        @AttributeOverride(name = "servicesFreeWifi", column = @Column(name = "features_service_free_wifi")),
        @AttributeOverride(name = "servicesValetService", column = @Column(name = "features_service_valet")),
        @AttributeOverride(name = "servicesMaintenance", column = @Column(name = "features_service_maintenance")),
        @AttributeOverride(name = "paymentsCardPayment", column = @Column(name = "features_payment_card")),
        @AttributeOverride(name = "paymentsMobilePayment", column = @Column(name = "features_payment_mobile")),
        @AttributeOverride(name = "paymentsMonthlyPasses", column = @Column(name = "features_payment_monthly_passes")),
        @AttributeOverride(name = "paymentsCorporateRates", column = @Column(name = "features_payment_corporate_rates"))
    })
    private FeaturesData features;

    @Column(length = 500)
    private String imageUrl;

    private String openingHours;

    private Double rating;
    private Integer reviewCount;

    public ParkingProfile() {
        super();
        this.status = ParkingStatus.ACTIVE;
        this.occupiedSpaces = 0;
        this.rating = 0.0;
        this.reviewCount = 0;
    }

    public ParkingProfile(String name, String ownerId, ParkingType type, String description,
                          LocationData location, Integer totalSpaces, Integer accessibleSpaces,
                          String phone, String email, PricingData pricing, FeaturesData features) {
        this();
        this.name = name;
        this.ownerId = ownerId;
        this.type = type;
        this.description = description;
        this.location = location;
        this.totalSpaces = totalSpaces;
        this.accessibleSpaces = accessibleSpaces;
        this.phone = phone;
        this.email = email;
        this.pricing = pricing;
        this.features = features;
    }
}
