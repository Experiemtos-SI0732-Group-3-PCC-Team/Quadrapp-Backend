package com.upc.quadrapp.profile.infrastructure.persistence.jpa.repositories;

import com.upc.quadrapp.profile.domain.model.aggregates.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverProfileRepository extends JpaRepository<Driver, Long> {
}
