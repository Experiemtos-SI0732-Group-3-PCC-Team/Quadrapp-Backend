package com.upc.quadrapp.parkingmanagement.domain.model.aggregates;

import com.upc.quadrapp.parkingmanagement.domain.model.commands.AddParkingSpotCommand;
import com.upc.quadrapp.parkingmanagement.domain.model.commands.CreateParkingCommand;
import com.upc.quadrapp.parkingmanagement.domain.model.commands.UpdateParkingAvailabilityCommand;
import com.upc.quadrapp.parkingmanagement.domain.model.valueobjects.ParkingSpotManager;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

public class Parking {
    @Setter
    @Getter
    private Long id;
    @Getter
    private Long ownerId;
    @Getter
    private String name;
    @Getter
    private String description;
    @Getter
    private String address;
    @Getter
    private double lat;
    @Getter
    private double lng;
    @Getter
    private float ratePerHour;
    private float rating;
    @Getter
    private Integer totalSpots;
    @Getter
    private Integer availableSpots;
    private Integer totalRows;
    private Integer totalColumns;
    @Getter
    private String imageUrl;
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
    }

    public Parking(Long id, Long ownerId, String name, String description, String address,
                   double lat, double lng, float ratePerHour, Integer totalRows, Integer totalColumns,
                   String imageUrl, ParkingSpotManager manager, Integer totalSpots, Integer availableSpots, float rating) {
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
