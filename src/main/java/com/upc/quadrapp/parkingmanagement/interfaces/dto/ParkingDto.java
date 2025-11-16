package com.upc.quadrapp.parkingmanagement.interfaces.dto;

import com.upc.quadrapp.parkingmanagement.domain.model.valueobjects.FeaturesData;
import com.upc.quadrapp.parkingmanagement.domain.model.valueobjects.LocationData;
import com.upc.quadrapp.parkingmanagement.domain.model.valueobjects.PricingData;

import java.util.List;

public class ParkingDto {
    public Long id;
    public Long ownerId;
    public String name;
    public String description;
    public String address;
    public double lat;
    public double lng;
    public float ratePerHour;
    public Integer totalRows;
    public Integer totalColumns;
    public String imageUrl;
    public List<ParkingSpotDto> spots;
    public LocationData location;
    public PricingData pricing;
    public FeaturesData features;
}