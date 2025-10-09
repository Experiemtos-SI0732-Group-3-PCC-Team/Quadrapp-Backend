package com.upc.quadrapp.parkingmanagement.infrastructure.persistence.repositories;

import com.upc.quadrapp.parkingmanagement.domain.model.aggregates.Parking;
import java.util.*;

public interface ParkingRepository {
    Parking save(Parking parking);
    Optional<Parking> findById(Long id);
    List<Parking> findAll();
}
