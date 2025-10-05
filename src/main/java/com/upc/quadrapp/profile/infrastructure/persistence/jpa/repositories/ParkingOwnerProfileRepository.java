package com.upc.quadrapp.profile.infrastructure.persistence.jpa.repositories;

import com.upc.quadrapp.profile.domain.model.aggregates.ParkingOwner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingOwnerProfileRepository extends JpaRepository<ParkingOwner, Long> {
}
