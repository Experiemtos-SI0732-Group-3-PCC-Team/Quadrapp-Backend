package com.upc.quadrapp.parkingmanagement.infrastructure.persistence.transformers;

import com.upc.quadrapp.parkingmanagement.domain.model.aggregates.Parking;
import com.upc.quadrapp.parkingmanagement.domain.model.aggregates.ParkingSpot;
import com.upc.quadrapp.parkingmanagement.domain.model.valueobjects.ParkingSpotManager;
import com.upc.quadrapp.parkingmanagement.infrastructure.persistence.entities.ParkingEntity;
import com.upc.quadrapp.parkingmanagement.infrastructure.persistence.entities.ParkingSpotEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ParkingTransformer {

    public ParkingEntity toEntity(Parking parking) {
        ParkingEntity entity = new ParkingEntity();
        entity.setId(parking.getId());
        entity.setOwnerId(parking.getOwnerId());
        entity.setName(parking.getName());
        entity.setDescription(parking.getDescription());
        entity.setAddress(parking.getAddress());
        entity.setLat(parking.getLat());
        entity.setLng(parking.getLng());
        entity.setRatePerHour(parking.getRatePerHour());
        entity.setRating(0f);
        entity.setTotalSpots(parking.getTotalSpots());
        entity.setAvailableSpots(parking.getAvailableSpots());
        entity.setTotalRows(parking.getTotalSpots());
        entity.setTotalColumns(parking.getTotalSpots());
        entity.setImageUrl(parking.getImageUrl());

        List<ParkingSpotEntity> spots = new ArrayList<>();
        for (ParkingSpot spot : parking.getParkingSpots()) {
            ParkingSpotEntity s = new ParkingSpotEntity();
            s.setId(spot.getId());
            s.setAvailable(spot.isAvailable());
            s.setRowIndex(spot.getRowIndex());
            s.setColumnIndex(spot.getColumnIndex());
            s.setLabel(spot.getLabel());
            s.setParking(entity);
            spots.add(s);
        }
        entity.setParkingSpots(spots);
        return entity;
    }

    public Parking toDomain(ParkingEntity entity) {
        ParkingSpotManager manager = new ParkingSpotManager();
        for (ParkingSpotEntity s : entity.getParkingSpots()) {
            ParkingSpot spot = new ParkingSpot(
                    entity.getId(),
                    s.getRowIndex(),
                    s.getColumnIndex(),
                    s.getLabel()
            );
            spot.setAvailability(s.isAvailable());
            manager.loadParkingSpot(spot);
        }

        return new Parking(
                entity.getId(),
                entity.getOwnerId(),
                entity.getName(),
                entity.getDescription(),
                entity.getAddress(),
                entity.getLat(),
                entity.getLng(),
                entity.getRatePerHour(),
                entity.getTotalRows(),
                entity.getTotalColumns(),
                entity.getImageUrl(),
                manager,
                entity.getTotalSpots(),
                entity.getAvailableSpots(),
                entity.getRating()
        );
    }
}
