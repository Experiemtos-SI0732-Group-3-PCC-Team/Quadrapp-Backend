package com.upc.quadrapp.parkingmanagement.domain.model.aggregates;

import com.upc.quadrapp.parkingmanagement.domain.model.commands.AddParkingSpotCommand;
import com.upc.quadrapp.parkingmanagement.domain.model.commands.CreateParkingCommand;
import com.upc.quadrapp.parkingmanagement.domain.model.commands.UpdateParkingAvailabilityCommand;
import com.upc.quadrapp.parkingmanagement.domain.model.valueobjects.ParkingSpotManager;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class ParkingTest {
    private CreateParkingCommand createCommand() {
        return new CreateParkingCommand(
                1L, "Estacionamiento Central", "Amplio y seguro", "Av. Principal 123",
                -12.04318, -77.02824, 3.5f, 5, 5, "https://img.test.com/parking.jpg"
        );
    }

    @Test
    void shouldCreateParkingWithDefaults() {
        // Arrange & Act
        Parking parking = new Parking(createCommand());

        // Assert
        assertEquals(1L, parking.getOwnerId());
        assertEquals("Estacionamiento Central", parking.getName());
        assertEquals(0, parking.getAvailableSpots());
        assertEquals(0, parking.getTotalSpots());
        assertEquals(3.5f, parking.getRatePerHour(), 0.001);
        assertNotNull(parking.getParkingSpots());
    }

    @Test
    void shouldAddParkingSpotSuccessfully() {
        // Arrange
        Parking parking = new Parking(createCommand());
        parking.setId(10L);

        // Act
        parking.addParkingSpot(new AddParkingSpotCommand(parking.getId(), 1, 1, "A1"));

        // Assert
        assertEquals(1, parking.getTotalSpots());
        assertEquals(1, parking.getAvailableSpots());
        assertEquals(1, parking.getParkingSpots().size());
    }

    @Test
    void shouldUpdateAvailabilityCorrectly() {
        // Arrange
        Parking parking = new Parking(createCommand());
        parking.setId(20L);
        parking.addParkingSpot(new AddParkingSpotCommand(parking.getId(), 1, 1, "A1"));
        UUID id = parking.getParkingSpots().get(0).getId();

        // Act & Assert
        parking.updateParkingAvailability(new UpdateParkingAvailabilityCommand(parking.getId(), id, false));
        assertEquals(0, parking.getAvailableSpots());

        parking.updateParkingAvailability(new UpdateParkingAvailabilityCommand(parking.getId(), id, true));
        assertEquals(1, parking.getAvailableSpots());
    }

    @Test
    void shouldThrowWhenUpdatingNonExistentSpot() {
        // Arrange
        Parking parking = new Parking(createCommand());
        parking.setId(30L);
        UUID fakeId = UUID.randomUUID();

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () ->
                parking.updateParkingAvailability(new UpdateParkingAvailabilityCommand(parking.getId(), fakeId, false))
        );
    }

    @Test
    void shouldCreateParkingWithFullConstructor() {
        // Arrange
        ParkingSpotManager manager = new ParkingSpotManager();

        // Act
        Parking parking = new Parking(
                10L, 1L, "MiParking", "Desc", "Calle 123",
                -12.1, -77.0, 2.5f, 3, 3, "url", manager,
                5, 2, 4.8f
        );

        // Assert
        assertEquals(10L, parking.getId());
        assertEquals(1L, parking.getOwnerId());
        assertEquals(5, parking.getTotalSpots());
        assertEquals(2, parking.getAvailableSpots());
        assertEquals("MiParking", parking.getName());
    }
}
