package com.upc.quadrapp.parkingmanagement.infrastructure.persistence.jpa.repositories;

import com.upc.quadrapp.parkingmanagement.domain.model.aggregates.ParkingProfile;
import com.upc.quadrapp.parkingmanagement.domain.model.valueobjects.ParkingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParkingProfileRepository extends JpaRepository<ParkingProfile, Long> {
    List<ParkingProfile> findByOwnerId(String ownerId);
    List<ParkingProfile> findByStatus(ParkingStatus status);
    List<ParkingProfile> findByOwnerIdAndStatus(String ownerId, ParkingStatus status);
}

