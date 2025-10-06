package com.upc.quadrapp.parkingmanagement.domain.model.commands;

import java.util.UUID;

public class UpdateParkingAvailabilityCommand {
    private final Long parkingId;
    private final UUID parkingSpotId;
    private final boolean available;

    public UpdateParkingAvailabilityCommand(Long parkingId, UUID parkingSpotId, boolean available) {
        this.parkingId = parkingId;
        this.parkingSpotId = parkingSpotId;
        this.available = available;
    }

    public Long parkingId() { return parkingId; }
    public UUID parkingSpotId() { return parkingSpotId; }
    public boolean available() { return available; }
}
