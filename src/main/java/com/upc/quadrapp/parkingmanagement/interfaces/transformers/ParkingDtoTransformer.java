package com.upc.quadrapp.parkingmanagement.interfaces.transformers;

import com.upc.quadrapp.parkingmanagement.domain.model.aggregates.Parking;
import com.upc.quadrapp.parkingmanagement.domain.model.aggregates.ParkingSpot;
import com.upc.quadrapp.parkingmanagement.domain.model.commands.CreateParkingCommand;
import com.upc.quadrapp.parkingmanagement.interfaces.dto.ParkingDto;
import com.upc.quadrapp.parkingmanagement.interfaces.dto.ParkingSpotDto;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ParkingDtoTransformer {

    public CreateParkingCommand toCreateCommand(ParkingDto dto) {
        return new CreateParkingCommand(
                dto.ownerId,
                dto.name,
                dto.description,
                dto.address,
                dto.lat,
                dto.lng,
                dto.ratePerHour,
                dto.totalRows,
                dto.totalColumns,
                dto.imageUrl
        );
    }

    public ParkingDto toDto(Parking parking) {
        ParkingDto dto = new ParkingDto();
        dto.id = parking.getId();
        dto.ownerId = parking.getOwnerId();
        dto.name = parking.getName();
        dto.description = parking.getAddress();
        dto.address = parking.getAddress();
        dto.lat = parking.getLat();
        dto.lng = parking.getLng();
        dto.ratePerHour = parking.getRatePerHour();
        dto.totalRows = parking.getTotalSpots();
        dto.totalColumns = parking.getTotalSpots();
        dto.imageUrl = parking.getImageUrl();
        dto.spots = parking.getParkingSpots().stream()
                .map(this::toSpotDto)
                .collect(Collectors.toList());
        return dto;
    }

    private ParkingSpotDto toSpotDto(ParkingSpot spot) {
        ParkingSpotDto dto = new ParkingSpotDto();
        dto.id = spot.getId();
        dto.available = spot.isAvailable();
        dto.row = spot.getRowIndex();
        dto.column = spot.getColumnIndex();
        dto.label = spot.getLabel();
        return dto;
    }
}
