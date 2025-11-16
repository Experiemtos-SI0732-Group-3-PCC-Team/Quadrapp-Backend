package com.upc.quadrapp.parkingmanagement.domain.model.aggregates;

import com.upc.quadrapp.parkingmanagement.domain.model.commands.AddParkingSpotCommand;
import com.upc.quadrapp.parkingmanagement.domain.model.commands.CreateParkingCommand;
import com.upc.quadrapp.parkingmanagement.domain.model.commands.UpdateParkingAvailabilityCommand;
import com.upc.quadrapp.parkingmanagement.domain.model.valueobjects.FeaturesData;
import com.upc.quadrapp.parkingmanagement.domain.model.valueobjects.LocationData;
import com.upc.quadrapp.parkingmanagement.domain.model.valueobjects.ParkingSpotManager;
import com.upc.quadrapp.parkingmanagement.domain.model.valueobjects.PricingData;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class ParkingTest {
    private CreateParkingCommand createCommand() {
        LocationData location = new LocationData(
                "Av. Principal 123",
                "Lima",
                "15001",
                "Lima",
                "Perú",
                -12.04318,
                -77.02824
        );

        PricingData.OperatingHours operatingHours = new PricingData.OperatingHours("08:00", "22:00");
        PricingData.OperatingDays operatingDays = new PricingData.OperatingDays(true, true, true, true, true, true, false);
        PricingData.Promotions promotions = new PricingData.Promotions(false, false, false);
        PricingData pricing = new PricingData(
                3.5,
                25.0,
                500.0,
                "PEN",
                "1h",
                false,
                operatingHours,
                operatingDays,
                promotions
        );

        FeaturesData.Security security = new FeaturesData.Security(true, true, true, false);
        FeaturesData.Amenities amenities = new FeaturesData.Amenities(true, false, false, false);
        FeaturesData.Services services = new FeaturesData.Services(false, true, false, false);
        FeaturesData.Payments payments = new FeaturesData.Payments(true, true, false, false);
        FeaturesData features = new FeaturesData(
                security,
                amenities,
                services,
                payments
        );

        return new CreateParkingCommand(
                1L, "Estacionamiento Central", "Amplio y seguro", "Av. Principal 123",
                -12.04318, -77.02824, 3.5f, 5, 5, "https://img.test.com/parking.jpg",
                location, pricing, features
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
        assertNotNull(parking.getLocation());
        assertNotNull(parking.getPricing());
        assertNotNull(parking.getFeatures());
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
        ParkingSpotManager manager = new ParkingSpotManager();
        LocationData location = new LocationData(
                "Calle 123",
                "Lima",
                "15001",
                "Lima",
                "Perú",
                -12.1,
                -77.0
        );
        PricingData.OperatingHours operatingHours = new PricingData.OperatingHours(null, null);
        PricingData.OperatingDays operatingDays = new PricingData.OperatingDays(true, true, true, true, true, true, true);
        PricingData.Promotions promotions = new PricingData.Promotions(false, false, false);
        PricingData pricing = new PricingData(
                2.5,
                20.0,
                400.0,
                "PEN",
                "SinLimite",
                true,
                operatingHours,
                operatingDays,
                promotions
        );
        FeaturesData.Security security = new FeaturesData.Security(false, true, true, false);
        FeaturesData.Amenities amenities = new FeaturesData.Amenities(true, true, false, false);
        FeaturesData.Services services = new FeaturesData.Services(true, false, false, false);
        FeaturesData.Payments payments = new FeaturesData.Payments(true, false, false, false);
        FeaturesData features = new FeaturesData(
                security,
                amenities,
                services,
                payments
        );
        Parking parking = new Parking(
                10L, 1L, "MiParking", "Desc", "Calle 123",
                -12.1, -77.0, 2.5f, 3, 3, "url", manager,
                5, 2, 4.8f, location, pricing, features
        );
        assertEquals(10L, parking.getId());
        assertEquals(1L, parking.getOwnerId());
        assertEquals(5, parking.getTotalSpots());
        assertEquals(2, parking.getAvailableSpots());
        assertEquals("MiParking", parking.getName());
        assertNotNull(parking.getLocation());
        assertNotNull(parking.getPricing());
        assertNotNull(parking.getFeatures());
        assertEquals("Calle 123", parking.getLocation().getAddressLine());
        assertEquals(2.5, parking.getPricing().getHourlyRate());
    }
}
