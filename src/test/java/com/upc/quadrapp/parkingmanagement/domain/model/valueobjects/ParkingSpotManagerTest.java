package com.upc.quadrapp.parkingmanagement.domain.model.valueobjects;

import com.upc.quadrapp.parkingmanagement.domain.model.aggregates.Parking;
import com.upc.quadrapp.parkingmanagement.domain.model.aggregates.ParkingSpot;
import com.upc.quadrapp.parkingmanagement.domain.model.commands.CreateParkingCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class ParkingSpotManagerTest {
    private ParkingSpotManager manager;
    private Parking parking;

    @BeforeEach
    void setup() {
        manager = new ParkingSpotManager();
        parking = new Parking(new CreateParkingCommand(
                1L, "Mi Parking", "Seguridad 24h", "Av. Los Pinos 456",
                -12.046, -77.03, 3.0f, 5, 5, "https://test.img/parking.png"
        ));
        parking.setId(10L);
    }

    @Test
    void shouldAddParkingSpotSuccessfully() {
        manager.addParkingSpot(parking, 1, 2, "A2");

        assertEquals(1, manager.getParkingSpots().size());
        ParkingSpot spot = manager.getParkingSpots().get(0);
        assertEquals(10L, spot.getParkingId());
        assertEquals("A2", spot.getLabel());
        assertTrue(spot.isAvailable());
    }

    @Test
    void shouldLoadExistingSpot() {
        ParkingSpot spot = new ParkingSpot(10L, 0, 0, "B1");
        manager.loadParkingSpot(spot);

        assertEquals(1, manager.getParkingSpots().size());
        assertTrue(manager.getParkingSpots().contains(spot));
    }

    @Test
    void shouldReturnUnmodifiableList() {
        manager.addParkingSpot(parking, 1, 1, "A1");
        var spots = manager.getParkingSpots();

        assertThrows(UnsupportedOperationException.class, () -> spots.add(new ParkingSpot(10L, 2, 2, "B2")));
    }

    @Test
    void shouldGetSpotById() {
        manager.addParkingSpot(parking, 1, 1, "A1");
        ParkingSpot spot = manager.getParkingSpots().get(0);

        Optional<ParkingSpot> found = manager.getParkingSpotById(spot.getId());
        assertTrue(found.isPresent());
        assertEquals(spot, found.get());
    }

    @Test
    void shouldReturnEmptyOptionalIfSpotNotFound() {
        Optional<ParkingSpot> found = manager.getParkingSpotById(UUID.randomUUID());
        assertTrue(found.isEmpty());
    }

    @Test
    void shouldUpdateExistingSpot() {
        manager.addParkingSpot(parking, 1, 1, "A1");
        ParkingSpot spot = manager.getParkingSpots().get(0);
        spot.setAvailability(false);

        manager.updateParkingSpot(spot);
        assertFalse(manager.getParkingSpots().get(0).isAvailable());
    }

    @Test
    void shouldThrowWhenUpdatingNonExistentSpot() {
        ParkingSpot nonExisting = new ParkingSpot(10L, 0, 0, "Z9");

        assertThrows(IllegalArgumentException.class, () -> manager.updateParkingSpot(nonExisting));
    }

    @Test
    void shouldCountAvailableSpotsCorrectly() {
        manager.addParkingSpot(parking, 1, 1, "A1");
        manager.addParkingSpot(parking, 1, 2, "A2");
        ParkingSpot first = manager.getParkingSpots().get(0);
        first.setAvailability(false);
        manager.updateParkingSpot(first);

        long count = manager.countAvailable();
        assertEquals(1, count);
    }
}
