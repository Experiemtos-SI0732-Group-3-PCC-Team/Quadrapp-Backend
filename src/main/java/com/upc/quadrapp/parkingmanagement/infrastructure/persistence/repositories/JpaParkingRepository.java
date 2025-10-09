package com.upc.quadrapp.parkingmanagement.infrastructure.persistence.repositories;

import com.upc.quadrapp.parkingmanagement.infrastructure.persistence.entities.ParkingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaParkingRepository extends JpaRepository<ParkingEntity, Long> {
}
