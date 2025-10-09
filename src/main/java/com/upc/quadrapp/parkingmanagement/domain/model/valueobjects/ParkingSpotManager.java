package com.upc.quadrapp.parkingmanagement.domain.model.valueobjects;

import com.upc.quadrapp.parkingmanagement.domain.model.aggregates.Parking;
import com.upc.quadrapp.parkingmanagement.domain.model.aggregates.ParkingSpot;

import java.util.*;

public class ParkingSpotManager {
    private final List<ParkingSpot> parkingSpots = new ArrayList<>();

    public ParkingSpotManager() { }

    public void addParkingSpot(Parking parking, int row, int column, String label) {
        ParkingSpot spot = new ParkingSpot(parking.getId(), row, column, label);
        parkingSpots.add(spot);
    }

    public void loadParkingSpot(ParkingSpot spot) {
        parkingSpots.add(spot);
    }

    public List<ParkingSpot> getParkingSpots() {
        return Collections.unmodifiableList(parkingSpots);
    }

    public Optional<ParkingSpot> getParkingSpotById(UUID id) {
        return parkingSpots.stream().filter(s -> s.getId().equals(id)).findFirst();
    }

    public void updateParkingSpot(ParkingSpot updated) {
        for (int i = 0; i < parkingSpots.size(); i++) {
            if (parkingSpots.get(i).getId().equals(updated.getId())) {
                parkingSpots.set(i, updated);
                return;
            }
        }
        throw new IllegalArgumentException("ParkingSpot not found: " + updated.getId());
    }

    public long countAvailable() {
        return parkingSpots.stream().filter(ParkingSpot::isAvailable).count();
    }
}
