package com.upc.quadrapp.parkingmanagement.domain.services;

import com.upc.quadrapp.parkingmanagement.domain.model.aggregates.Parking;

import java.util.List;
import java.util.Optional;

public interface ParkingQueryService {
    List<Parking> findAll();
    Optional<Parking> findById(Long id);
    List<Parking> findAvailableNear(double lat, double lng, double radiusKm);
}
