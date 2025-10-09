package com.upc.quadrapp.profile.domain.model.aggregates;

import com.upc.quadrapp.profile.domain.model.commands.CreateParkingOwnerProfileCommand;
import com.upc.quadrapp.profile.domain.model.valueobjects.Phone;
import com.upc.quadrapp.profile.domain.model.valueobjects.Ruc;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ParkingOwnerTest {

    @Test
    void shouldCreateParkingOwnerFromCommand() {
        // Arrange
        CreateParkingOwnerProfileCommand command = new CreateParkingOwnerProfileCommand(
                "Carlos Mendez",
                "Arequipa",
                "Peru",
                new Phone("+51912345678"),
                "Parking Pro S.A.C.",
                new Ruc("20123456789"),
                2L
        );

        // Act
        ParkingOwner owner = new ParkingOwner(command);

        // Assert
        assertEquals("Carlos Mendez", owner.getFullName());
        assertEquals("Arequipa", owner.getCity());
        assertEquals("Peru", owner.getCountry());
        assertEquals("+51912345678", owner.getPhone().phone());
        assertEquals("Parking Pro S.A.C.", owner.getCompanyName());
        assertEquals("20123456789", owner.getRuc().ruc());
        assertEquals(2L, owner.getUserId());
        assertNotNull(owner.getCreatedAt());
        assertNotNull(owner.getUpdatedAt());
    }

    @Test
    void shouldThrowExceptionWhenInvalidRucProvided() {
        // Arrange
        assertThrows(IllegalArgumentException.class, () -> new Ruc("123"));
    }

    @Test
    void shouldThrowExceptionWhenInvalidPhoneProvided() {
        // Arrange
        assertThrows(IllegalArgumentException.class, () -> new Phone("abc123"));
    }
}
