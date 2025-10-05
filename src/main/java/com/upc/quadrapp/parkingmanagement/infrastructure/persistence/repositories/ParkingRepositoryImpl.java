package com.upc.quadrapp.parkingmanagement.infrastructure.persistence.repositories;

import com.upc.quadrapp.parkingmanagement.domain.model.aggregates.Parking;
import com.upc.quadrapp.parkingmanagement.infrastructure.persistence.entities.ParkingEntity;
import com.upc.quadrapp.parkingmanagement.infrastructure.persistence.transformers.ParkingTransformer;
import org.springframework.stereotype.Repository;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class ParkingRepositoryImpl implements ParkingRepository {

    private final JpaParkingRepository jpaRepository;
    private final ParkingTransformer transformer;

    public ParkingRepositoryImpl(JpaParkingRepository jpaRepository, ParkingTransformer transformer) {
        this.jpaRepository = jpaRepository;
        this.transformer = transformer;
    }

    @Override
    public Parking save(Parking parking) {
        ParkingEntity entity = transformer.toEntity(parking);
        ParkingEntity saved = jpaRepository.save(entity);
        return transformer.toDomain(saved);
    }

    @Override
    public Optional<Parking> findById(Long id) {
        return jpaRepository.findById(id).map(transformer::toDomain);
    }

    @Override
    public List<Parking> findAll() {
        return jpaRepository.findAll()
                .stream()
                .map(transformer::toDomain)
                .collect(Collectors.toList());
    }
}
