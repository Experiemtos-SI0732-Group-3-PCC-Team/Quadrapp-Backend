package com.upc.quadrapp.parkingmanagement.application.services.impl;

import com.upc.quadrapp.parkingmanagement.domain.model.aggregates.Parking;
import com.upc.quadrapp.parkingmanagement.domain.services.ParkingQueryService;
import com.upc.quadrapp.parkingmanagement.infrastructure.persistence.repositories.ParkingRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParkingQueryServiceImpl implements ParkingQueryService {

    private final ParkingRepository parkingRepository;

    public ParkingQueryServiceImpl(ParkingRepository parkingRepository) {
        this.parkingRepository = parkingRepository;
    }

    @Override
    public List<Parking> findAll() {
        return parkingRepository.findAll();
    }

    @Override
    public Optional<Parking> findById(Long id) {
        return parkingRepository.findById(id);
    }

    @Override
    public List<Parking> findAvailableNear(double lat, double lng, double radiusKm) {
        return parkingRepository.findAll();
    }
}
