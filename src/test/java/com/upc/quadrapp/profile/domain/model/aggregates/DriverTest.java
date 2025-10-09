package com.upc.quadrapp.profile.domain.model.aggregates;

import com.upc.quadrapp.profile.domain.model.commands.CreateDriverProfileCommand;
import com.upc.quadrapp.profile.domain.model.valueobjects.Dni;
import com.upc.quadrapp.profile.domain.model.valueobjects.Phone;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DriverTest {

    @Test
    void shouldCreateDriverFromCommand() {
        // Arrange
        CreateDriverProfileCommand command = new CreateDriverProfileCommand(
                "Leonardo Solis",
                "Lima",
                "Peru",
                new Phone("+51987654321"),
                new Dni("12345678"),
                1L
        );

        // Act
        Driver driver = new Driver(command);

        // Assert
        assertEquals("Leonardo Solis", driver.getFullName());
        assertEquals("Lima", driver.getCity());
        assertEquals("Peru", driver.getCountry());
        assertEquals("12345678", driver.getDni().dni());
        assertEquals("+51987654321", driver.getPhone().phone());
        assertEquals(1L, driver.getUserId());
        assertNotNull(driver.getCreatedAt());
        assertNotNull(driver.getUpdatedAt());
    }

    @Test
    void shouldThrowExceptionWhenInvalidDniProvided() {
        // Arrange
        assertThrows(IllegalArgumentException.class, () -> new Dni("12A45678"));
    }

    @Test
    void shouldThrowExceptionWhenInvalidPhoneProvided() {
        // Arrange
        assertThrows(IllegalArgumentException.class, () -> new Phone("invalid"));
    }
}
