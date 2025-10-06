package com.upc.quadrapp.parkingmanagement.interfaces.dto;

import java.util.UUID;

public class ParkingSpotDto {
    public UUID id;
    public boolean available;
    public Integer row;
    public Integer column;
    public String label;
}
