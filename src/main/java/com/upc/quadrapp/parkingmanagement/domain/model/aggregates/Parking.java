package com.upc.quadrapp.parkingmanagement.domain.model.aggregates;

import com.upc.quadrapp.parkingmanagement.domain.model.commands.AddParkingSpotCommand;
import com.upc.quadrapp.parkingmanagement.domain.model.commands.CreateParkingCommand;
import com.upc.quadrapp.parkingmanagement.domain.model.commands.UpdateParkingAvailabilityCommand;
import com.upc.quadrapp.parkingmanagement.domain.model.valueobjects.FeaturesData;
import com.upc.quadrapp.parkingmanagement.domain.model.valueobjects.LocationData;
import com.upc.quadrapp.parkingmanagement.domain.model.valueobjects.ParkingSpotManager;
import com.upc.quadrapp.parkingmanagement.domain.model.valueobjects.PricingData;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import java.util.*;

@Entity
public class Parking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;
    @Getter
    @Setter
    private Long ownerId;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private String description;
    @Getter
    @Setter
    private String address;
    @Getter
    @Setter
    private double lat;
    @Getter
    @Setter
    private double lng;
    @Getter
    @Setter
    private float ratePerHour;
    @Getter
    @Setter
    private float rating;
    @Getter
    @Setter
    private Integer totalSpots;
    @Getter
    @Setter
    private Integer availableSpots;
    @Getter
    @Setter
    private Integer totalRows;
    @Getter
    @Setter
    private Integer totalColumns;
    @Getter
    @Setter
    private String imageUrl;
    @Embedded
    @Getter
    @Setter
    private LocationData location;
    @Embedded
    @Getter
    @Setter
    private PricingData pricing;
    @Embedded
    @Getter
    @Setter
    private FeaturesData features;
    @Transient
    private ParkingSpotManager parkingSpotManager;

    public Parking(CreateParkingCommand cmd) {
        this.ownerId = cmd.ownerId();
        this.name = cmd.name();
        this.description = cmd.description();
        this.address = cmd.address();
        this.lat = cmd.lat();
        this.lng = cmd.lng();
        this.ratePerHour = cmd.ratePerHour();
        this.totalRows = cmd.totalRows();
        this.totalColumns = cmd.totalColumns();
        this.imageUrl = cmd.imageUrl();
        this.parkingSpotManager = new ParkingSpotManager();
        this.totalSpots = 0;
        this.availableSpots = 0;
        this.rating = 0f;
        this.location = cmd.location();
        this.pricing = cmd.pricing();
        this.features = cmd.features();
    }

    public Parking(Long id, Long ownerId, String name, String description, String address,
                   double lat, double lng, float ratePerHour, Integer totalRows, Integer totalColumns,
                   String imageUrl, ParkingSpotManager manager, Integer totalSpots, Integer availableSpots, float rating,
                   LocationData location, PricingData pricing, FeaturesData features) {
        this.id = id;
        this.ownerId = ownerId;
        this.name = name;
        this.description = description;
        this.address = address;
        this.lat = lat;
        this.lng = lng;
        this.ratePerHour = ratePerHour;
        this.totalRows = totalRows;
        this.totalColumns = totalColumns;
        this.imageUrl = imageUrl;
        this.parkingSpotManager = manager == null ? new ParkingSpotManager() : manager;
        this.totalSpots = totalSpots == null ? 0 : totalSpots;
        this.availableSpots = availableSpots == null ? 0 : availableSpots;
        this.rating = rating;
        this.location = location;
        this.pricing = pricing;
        this.features = features;
    }

    // Constructor sin argumentos requerido por JPA/Hibernate
    protected Parking() {
        // Para uso exclusivo de JPA/Hibernate
    }

    public void addParkingSpot(AddParkingSpotCommand cmd) {
        parkingSpotManager.addParkingSpot(this, cmd.row(), cmd.column(), cmd.label());
        totalSpots = totalSpots + 1;
        availableSpots = availableSpots + 1;
    }

    public void updateParkingAvailability(UpdateParkingAvailabilityCommand cmd) {
        Optional<ParkingSpot> opt = parkingSpotManager.getParkingSpotById(cmd.parkingSpotId());
        if (opt.isEmpty()) throw new IllegalArgumentException("ParkingSpot not found");
        ParkingSpot spot = opt.get();
        boolean previous = spot.isAvailable();
        spot.setAvailability(cmd.available());
        parkingSpotManager.updateParkingSpot(spot);
        // recalculate availableSpots
        long avail = parkingSpotManager.countAvailable();
        this.availableSpots = (int) avail;
    }

    public List<ParkingSpot> getParkingSpots() { return parkingSpotManager.getParkingSpots(); }
    public ParkingSpot getParkingSpot(java.util.UUID id) {
        return parkingSpotManager.getParkingSpotById(id).orElse(null);
    }
}
