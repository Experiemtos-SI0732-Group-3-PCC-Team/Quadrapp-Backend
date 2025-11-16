package com.upc.quadrapp.parkingmanagement.application.services;

import com.upc.quadrapp.parkingmanagement.domain.model.aggregates.ParkingProfile;
import com.upc.quadrapp.parkingmanagement.domain.model.valueobjects.FeaturesData;
import com.upc.quadrapp.parkingmanagement.domain.model.valueobjects.LocationData;
import com.upc.quadrapp.parkingmanagement.domain.model.valueobjects.PricingData;
import com.upc.quadrapp.parkingmanagement.infrastructure.persistence.jpa.repositories.ParkingProfileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ParkingProfileService {
    private static final Logger logger = LoggerFactory.getLogger(ParkingProfileService.class);

    private final ParkingProfileRepository repository;

    public ParkingProfileService(ParkingProfileRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public ParkingProfile createParkingProfile(ParkingProfile profile, String userId) {
        // Validar que el ownerId coincida con el usuario autenticado
        if (!profile.getOwnerId().equals(userId)) {
            logger.warn("User {} attempted to create parking with ownerId {}", userId, profile.getOwnerId());
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Cannot create parking for another owner");
        }

        // Inicializar features y pricing si son null
        if (profile.getFeatures() == null) {
            profile.setFeatures(new FeaturesData());
        }
        if (profile.getPricing() == null) {
            profile.setPricing(new PricingData());
        }

        logger.info("Creating parking profile: name={}, ownerId={}, userId={}",
            profile.getName(), profile.getOwnerId(), userId);
        logger.debug("Location data before save: {}", profile.getLocation());
        logger.debug("Pricing data before save: {}", profile.getPricing());
        logger.debug("Features data before save: {}", profile.getFeatures());

        ParkingProfile saved = repository.save(profile);

        logger.info("Parking profile created with ID: {}", saved.getId());
        logger.debug("Location data after save: {}", saved.getLocation());
        logger.debug("Pricing data after save: {}", saved.getPricing());
        logger.debug("Features data after save: {}", saved.getFeatures());

        return saved;
    }

    @Transactional
    public ParkingProfile updateParkingProfile(Long id, ParkingProfile updatedProfile, String userId, boolean isAdmin) {
        ParkingProfile existing = repository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Parking profile not found"));

        // Verificar permisos: admin puede todo, usuario solo sus propios parkings
        if (!isAdmin && !existing.getOwnerId().equals(userId)) {
            logger.warn("User {} attempted to update parking {} owned by {}", userId, id, existing.getOwnerId());
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Cannot update parking owned by another user");
        }

        // Actualizar campos básicos
        existing.setName(updatedProfile.getName());
        existing.setType(updatedProfile.getType());
        existing.setStatus(updatedProfile.getStatus());
        existing.setDescription(updatedProfile.getDescription());

        // Solo actualizar location si NO es null (para no sobrescribir datos existentes)
        if (updatedProfile.getLocation() != null) {
            existing.setLocation(updatedProfile.getLocation());
        }

        existing.setTotalSpaces(updatedProfile.getTotalSpaces());
        existing.setAccessibleSpaces(updatedProfile.getAccessibleSpaces());
        existing.setOccupiedSpaces(updatedProfile.getOccupiedSpaces());
        existing.setPhone(updatedProfile.getPhone());
        existing.setEmail(updatedProfile.getEmail());
        existing.setWebsite(updatedProfile.getWebsite());

        // Actualizar pricing y features, inicializando si son null
        if (updatedProfile.getPricing() != null) {
            existing.setPricing(updatedProfile.getPricing());
        } else if (existing.getPricing() == null) {
            existing.setPricing(new PricingData());
        }
        if (updatedProfile.getFeatures() != null) {
            existing.setFeatures(updatedProfile.getFeatures());
        } else if (existing.getFeatures() == null) {
            existing.setFeatures(new FeaturesData());
        }

        if (updatedProfile.getImageUrl() != null) {
            existing.setImageUrl(updatedProfile.getImageUrl());
        }
        existing.setOpeningHours(updatedProfile.getOpeningHours());

        logger.info("Updating parking profile: id={}, ownerId={}, userId={}", id, existing.getOwnerId(), userId);
        logger.debug("Location data before save: {}", existing.getLocation());
        logger.debug("Pricing data before save: {}", existing.getPricing());
        logger.debug("Features data before save: {}", existing.getFeatures());

        ParkingProfile saved = repository.save(existing);

        logger.info("Parking profile updated with ID: {}", saved.getId());
        logger.debug("Location data after save: {}", saved.getLocation());
        logger.debug("Pricing data after save: {}", saved.getPricing());
        logger.debug("Features data after save: {}", saved.getFeatures());

        return saved;
    }

    @Transactional
    public void deleteParkingProfile(Long id, String userId, boolean isAdmin) {
        ParkingProfile existing = repository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Parking profile not found"));

        // Verificar permisos
        if (!isAdmin && !existing.getOwnerId().equals(userId)) {
            logger.warn("User {} attempted to delete parking {} owned by {}", userId, id, existing.getOwnerId());
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Cannot delete parking owned by another user");
        }

        logger.info("Deleting parking profile: id={}, ownerId={}, userId={}", id, existing.getOwnerId(), userId);

        repository.delete(existing);
    }

    public List<ParkingProfile> listParkingProfiles(String userId, boolean isAdmin, String ownerIdFilter) {
        // Si es admin y no hay filtro, devolver todos
        if (isAdmin && ownerIdFilter == null) {
            return repository.findAll();
        }

        // Si hay filtro de ownerId, usarlo (admin puede filtrar por cualquier owner)
        if (ownerIdFilter != null) {
            if (!isAdmin && !ownerIdFilter.equals(userId)) {
                logger.warn("User {} attempted to list parkings for ownerId {}", userId, ownerIdFilter);
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Cannot list parkings for another owner");
            }
            return repository.findByOwnerId(ownerIdFilter);
        }

        // Usuario normal sin filtro: devolver solo sus parkings
        return repository.findByOwnerId(userId);
    }

    public Optional<ParkingProfile> findById(Long id, String userId, boolean isAdmin) {
        Optional<ParkingProfile> profile = repository.findById(id);

        if (profile.isPresent() && !isAdmin && !profile.get().getOwnerId().equals(userId)) {
            logger.warn("User {} attempted to access parking {} owned by {}", userId, id, profile.get().getOwnerId());
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Cannot access parking owned by another user");
        }

        return profile;
    }

    public ParkingProfile findByIdOrThrow(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Parking profile not found"));
    }

    @Transactional
    public ParkingProfile updateLocation(Long parkingId, LocationData location, String userId, boolean isAdmin) {
        ParkingProfile profile = repository.findById(parkingId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Parking profile not found"));

        // Verificar permisos
        if (!isAdmin && !profile.getOwnerId().equals(userId)) {
            logger.warn("User {} attempted to update location of parking {} owned by {}", userId, parkingId, profile.getOwnerId());
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Cannot update parking owned by another user");
        }

        logger.info("Updating location for parking: id={}, ownerId={}, userId={}", parkingId, profile.getOwnerId(), userId);
        logger.debug("Location data BEFORE update: {}", profile.getLocation());
        logger.debug("New location data to set: {}", location);

        profile.setLocation(location);

        ParkingProfile saved = repository.save(profile);
        repository.flush(); // Force immediate persistence

        logger.info("Location updated and flushed for parking ID: {}", saved.getId());
        logger.debug("Location data AFTER save and flush: {}", saved.getLocation());

        // Verify by re-reading from database
        ParkingProfile verified = repository.findById(parkingId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Parking not found after save"));
        logger.debug("Location data VERIFIED from DB: {}", verified.getLocation());

        return verified;
    }

    @Transactional
    public ParkingProfile updatePricing(Long parkingId, PricingData pricing, String userId, boolean isAdmin) {
        ParkingProfile profile = repository.findById(parkingId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Parking profile not found"));

        // Verificar permisos
        if (!isAdmin && !profile.getOwnerId().equals(userId)) {
            logger.warn("User {} attempted to update pricing of parking {} owned by {}", userId, parkingId, profile.getOwnerId());
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Cannot update parking owned by another user");
        }

        logger.info("Updating pricing for parking: id={}, ownerId={}, userId={}", parkingId, profile.getOwnerId(), userId);
        logger.debug("Pricing data BEFORE update: {}", profile.getPricing());
        logger.debug("New pricing data to set: {}", pricing);

        profile.setPricing(pricing);

        ParkingProfile saved = repository.save(profile);
        repository.flush(); // Force immediate persistence

        logger.info("Pricing updated and flushed for parking ID: {}", saved.getId());
        logger.debug("Pricing data AFTER save and flush: {}", saved.getPricing());

        // Verify by re-reading from database
        ParkingProfile verified = repository.findById(parkingId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Parking not found after save"));
        logger.debug("Pricing data VERIFIED from DB: {}", verified.getPricing());

        return verified;
    }

    @Transactional
    public ParkingProfile updateFeatures(Long parkingId, FeaturesData features, String userId, boolean isAdmin) {
        ParkingProfile profile = repository.findById(parkingId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Parking profile not found"));

        // Verificar permisos
        if (!isAdmin && !profile.getOwnerId().equals(userId)) {
            logger.warn("User {} attempted to update features of parking {} owned by {}", userId, parkingId, profile.getOwnerId());
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Cannot update parking owned by another user");
        }

        logger.info("Updating features for parking: id={}, ownerId={}, userId={}", parkingId, profile.getOwnerId(), userId);
        logger.debug("Features data BEFORE update: {}", profile.getFeatures());
        logger.debug("New features data to set: {}", features);

        profile.setFeatures(features);

        ParkingProfile saved = repository.save(profile);
        repository.flush(); // Force immediate persistence

        logger.info("Features updated and flushed for parking ID: {}", saved.getId());
        logger.debug("Features data AFTER save and flush: {}", saved.getFeatures());

        // Verify by re-reading from database
        ParkingProfile verified = repository.findById(parkingId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Parking not found after save"));
        logger.debug("Features data VERIFIED from DB: {}", verified.getFeatures());

        return verified;
    }
}
