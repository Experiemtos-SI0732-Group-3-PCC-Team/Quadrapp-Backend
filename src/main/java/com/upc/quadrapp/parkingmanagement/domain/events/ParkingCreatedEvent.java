package com.upc.quadrapp.parkingmanagement.domain.events;

public class ParkingCreatedEvent {
    private final Long parkingId;
    private final Long ownerId;

    public ParkingCreatedEvent(Long parkingId, Long ownerId) {
        this.parkingId = parkingId;
        this.ownerId = ownerId;
    }

    public Long parkingId() { return parkingId; }
    public Long ownerId() { return ownerId; }
}
