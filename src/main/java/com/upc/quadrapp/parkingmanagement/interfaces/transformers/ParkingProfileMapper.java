package com.upc.quadrapp.parkingmanagement.interfaces.transformers;

import com.upc.quadrapp.parkingmanagement.domain.model.aggregates.ParkingProfile;
import com.upc.quadrapp.parkingmanagement.domain.model.valueobjects.*;
import com.upc.quadrapp.parkingmanagement.interfaces.dto.ParkingProfileDto;
import org.springframework.stereotype.Component;

@Component
public class ParkingProfileMapper {

    public ParkingProfileDto toDto(ParkingProfile entity) {
        if (entity == null) return null;

        ParkingProfileDto dto = new ParkingProfileDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setOwnerId(entity.getOwnerId());
        dto.setType(entity.getType() != null ? entity.getType().name() : null);
        dto.setStatus(entity.getStatus() != null ? entity.getStatus().name() : null);
        dto.setDescription(entity.getDescription());
        dto.setLocation(entity.getLocation() != null ? entity.getLocation() : new LocationData());
        dto.setTotalSpaces(entity.getTotalSpaces());
        dto.setAccessibleSpaces(entity.getAccessibleSpaces());
        dto.setOccupiedSpaces(entity.getOccupiedSpaces());
        dto.setPhone(entity.getPhone());
        dto.setEmail(entity.getEmail());
        dto.setWebsite(entity.getWebsite());
        dto.setPricing(entity.getPricing() != null ? entity.getPricing() : new PricingData());
        dto.setFeatures(entity.getFeatures() != null ? entity.getFeatures() : new FeaturesData());
        dto.setImageUrl(entity.getImageUrl());
        dto.setOpeningHours(entity.getOpeningHours());
        dto.setRating(entity.getRating());
        dto.setReviewCount(entity.getReviewCount());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());

        return dto;
    }

    public ParkingProfile toEntity(ParkingProfileDto dto) {
        if (dto == null) return null;

        ParkingProfile entity = new ParkingProfile();
        entity.setName(dto.getName());
        entity.setOwnerId(dto.getOwnerId());

        // Convertir type con soporte para español e inglés
        if (dto.getType() != null) {
            entity.setType(parseType(dto.getType()));
        }

        // Convertir status con soporte para español e inglés
        if (dto.getStatus() != null) {
            entity.setStatus(parseStatus(dto.getStatus()));
        }

        entity.setDescription(dto.getDescription());
        entity.setLocation(dto.getLocation());
        entity.setTotalSpaces(dto.getTotalSpaces());
        entity.setAccessibleSpaces(dto.getAccessibleSpaces());
        entity.setOccupiedSpaces(dto.getOccupiedSpaces());
        entity.setPhone(dto.getPhone());
        entity.setEmail(dto.getEmail());
        entity.setWebsite(dto.getWebsite());
        entity.setPricing(dto.getPricing());
        entity.setFeatures(dto.getFeatures());
        entity.setImageUrl(dto.getImageUrl());
        entity.setOpeningHours(dto.getOpeningHours());
        entity.setRating(dto.getRating());
        entity.setReviewCount(dto.getReviewCount());

        return entity;
    }

    private ParkingType parseType(String type) {
        if (type == null) return ParkingType.PUBLIC;

        String normalized = type.toUpperCase().trim();

        // Mapeo de español a inglés
        return switch (normalized) {
            case "PUBLICO", "PÚBLICO", "PUBLIC" -> ParkingType.PUBLIC;
            case "PRIVADO", "PRIVATE" -> ParkingType.PRIVATE;
            case "RESIDENCIAL", "RESIDENTIAL" -> ParkingType.RESIDENTIAL;
            case "COMERCIAL", "COMMERCIAL" -> ParkingType.COMMERCIAL;
            default -> {
                try {
                    yield ParkingType.valueOf(normalized);
                } catch (IllegalArgumentException e) {
                    yield ParkingType.PUBLIC; // default
                }
            }
        };
    }

    private ParkingStatus parseStatus(String status) {
        if (status == null) return ParkingStatus.ACTIVE;

        String normalized = status.toUpperCase().trim();

        // Mapeo de español a inglés
        return switch (normalized) {
            case "ACTIVO", "ACTIVE" -> ParkingStatus.ACTIVE;
            case "INACTIVO", "INACTIVE" -> ParkingStatus.INACTIVE;
            case "MANTENIMIENTO", "MAINTENANCE" -> ParkingStatus.MAINTENANCE;
            case "LLENO", "FULL" -> ParkingStatus.FULL;
            default -> {
                try {
                    yield ParkingStatus.valueOf(normalized);
                } catch (IllegalArgumentException e) {
                    yield ParkingStatus.ACTIVE; // default
                }
            }
        };
    }
}
