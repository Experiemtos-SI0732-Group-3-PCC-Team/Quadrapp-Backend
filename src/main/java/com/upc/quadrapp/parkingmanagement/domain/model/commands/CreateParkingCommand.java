package com.upc.quadrapp.parkingmanagement.domain.model.commands;

import com.upc.quadrapp.parkingmanagement.domain.model.valueobjects.FeaturesData;
import com.upc.quadrapp.parkingmanagement.domain.model.valueobjects.LocationData;
import com.upc.quadrapp.parkingmanagement.domain.model.valueobjects.PricingData;

public class CreateParkingCommand {
    private final Long ownerId;
    private final String name;
    private final String description;
    private final String address;
    private final double lat;
    private final double lng;
    private final float ratePerHour;
    private final Integer totalRows;
    private final Integer totalColumns;
    private final String imageUrl;
    private final LocationData location;
    private final PricingData pricing;
    private final FeaturesData features;

    public CreateParkingCommand(Long ownerId, String name, String description, String address,
                                double lat, double lng, float ratePerHour,
                                Integer totalRows, Integer totalColumns, String imageUrl,
                                LocationData location, PricingData pricing, FeaturesData features) {
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
        this.location = location;
        this.pricing = pricing;
        this.features = features;
    }

    public Long ownerId() { return ownerId; }
    public String name() { return name; }
    public String description() { return description; }
    public String address() { return address; }
    public double lat() { return lat; }
    public double lng() { return lng; }
    public float ratePerHour() { return ratePerHour; }
    public Integer totalRows() { return totalRows; }
    public Integer totalColumns() { return totalColumns; }
    public String imageUrl() { return imageUrl; }
    public LocationData location() { return location; }
    public PricingData pricing() { return pricing; }
    public FeaturesData features() { return features; }
}
