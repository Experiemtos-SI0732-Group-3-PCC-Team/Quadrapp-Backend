package com.upc.quadrapp.parkingmanagement.interfaces.dto;

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
}