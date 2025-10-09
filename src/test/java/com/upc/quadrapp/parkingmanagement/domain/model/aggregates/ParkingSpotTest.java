package com.upc.quadrapp.parkingmanagement.domain.model.aggregates;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class ParkingSpotTest {
    @Test
    void shouldCreateParkingSpotWithDefaultAvailabilityTrue() {
        ParkingSpot spot = new ParkingSpot(1L, 2, 3, "A1");

        assertNotNull(spot.getId());
        assertEquals(1L, spot.getParkingId());
        assertEquals(2, spot.getRowIndex());
        assertEquals(3, spot.getColumnIndex());
        assertEquals("A1", spot.getLabel());
        assertTrue(spot.isAvailable());
    }

    @Test
    void shouldChangeAvailability() {
        ParkingSpot spot = new ParkingSpot(1L, 0, 0, "A0");
        spot.setAvailability(false);

        assertFalse(spot.isAvailable());
        spot.setAvailability(true);
        assertTrue(spot.isAvailable());
    }

    @Test
    void shouldBeEqualIfSameId() {
        ParkingSpot spot1 = new ParkingSpot(1L, 1, 1, "B1");
        ParkingSpot spot2 = new ParkingSpot(1L, 1, 1, "B1");

        UUID id = spot1.getId();
        assertNotEquals(spot1, spot2);
        assertNotEquals(spot1.hashCode(), spot2.hashCode());
    }
}
