package com.upc.quadrapp.parkingmanagement.interfaces.controllers;

import com.upc.quadrapp.parkingmanagement.application.services.ParkingProfileService;
import com.upc.quadrapp.parkingmanagement.interfaces.dto.ParkingProfileDto;
import com.upc.quadrapp.parkingmanagement.interfaces.transformers.ParkingProfileMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1/parkings", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Parking Profiles", description = "Parking Profile Management Endpoints")
@SecurityRequirement(name = "bearerAuth")
public class ParkingProfileController {

    private final ParkingProfileService service;
    private final ParkingProfileMapper mapper;

    public ParkingProfileController(ParkingProfileService service, ParkingProfileMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    @Operation(summary = "List all parking profiles", description = "Returns parking profiles filtered by owner")
    public ResponseEntity<List<ParkingProfileDto>> listParkingProfiles(
            Authentication authentication,
            @RequestParam(required = false) String ownerId) {

        String userId = authentication.getName();
        boolean isAdmin = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals("ROLE_ADMIN"));

        List<ParkingProfileDto> profiles = service.listParkingProfiles(userId, isAdmin, ownerId)
                .stream()
                .map(mapper::toDto)
                .toList();

        // Devolver directamente el array, sin envoltorio { data, total }
        return ResponseEntity.ok(profiles);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get parking profile by ID")
    public ResponseEntity<ParkingProfileDto> getParkingProfile(
            @PathVariable Long id,
            Authentication authentication) {

        String userId = authentication.getName();
        boolean isAdmin = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals("ROLE_ADMIN"));

        return service.findById(id, userId, isAdmin)
                .map(mapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "Create parking profile")
    public ResponseEntity<ParkingProfileDto> createParkingProfile(
            @Valid @RequestBody ParkingProfileDto dto,
            Authentication authentication) {

        String userId = authentication.getName();

        // Log del DTO recibido ANTES de mapear
        System.out.println("=== RECEIVED DTO FROM FRONTEND ===");
        System.out.println("DTO Location: " + dto.getLocation());
        System.out.println("DTO Pricing: " + dto.getPricing());
        System.out.println("DTO Features: " + dto.getFeatures());

        var entity = mapper.toEntity(dto);
        entity.setOwnerId(userId);

        // Log para debugging - ver qué datos vienen del frontend
        System.out.println("=== CREATING PARKING PROFILE ===");
        System.out.println("User: " + userId);
        System.out.println("Name: " + entity.getName());
        System.out.println("Location (before save): " + entity.getLocation());
        System.out.println("Pricing (before save): " + entity.getPricing());
        System.out.println("Features (before save): " + entity.getFeatures());


        var created = service.createParkingProfile(entity, userId);

        // Log después de guardar
        System.out.println("=== AFTER SAVE ===");
        System.out.println("ID: " + created.getId());
        System.out.println("Location (after save): " + created.getLocation());
        System.out.println("Pricing (after save): " + created.getPricing());
        System.out.println("Features (after save): " + created.getFeatures());

        return new ResponseEntity<>(mapper.toDto(created), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "Update parking profile")
    public ResponseEntity<ParkingProfileDto> updateParkingProfile(
            @PathVariable Long id,
            @Valid @RequestBody ParkingProfileDto dto,
            Authentication authentication) {

        String userId = authentication.getName();
        boolean isAdmin = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals("ROLE_ADMIN"));

        // Log para debugging
        System.out.println("=== UPDATING PARKING PROFILE ID: " + id + " ===");
        System.out.println("Location (from DTO): " + dto.getLocation());
        System.out.println("Pricing (from DTO): " + dto.getPricing());
        System.out.println("Features (from DTO): " + dto.getFeatures());

        var entity = mapper.toEntity(dto);

        // No sobrescribir con null - mantener valores existentes si vienen null
        // Este comportamiento será manejado por el servicio

        var updated = service.updateParkingProfile(id, entity, userId, isAdmin);

        System.out.println("=== AFTER UPDATE ===");
        System.out.println("Location (after save): " + updated.getLocation());
        System.out.println("Pricing (after save): " + updated.getPricing());
        System.out.println("Features (after save): " + updated.getFeatures());

        return ResponseEntity.ok(mapper.toDto(updated));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete parking profile")
    public ResponseEntity<Void> deleteParkingProfile(
            @PathVariable Long id,
            Authentication authentication) {

        String userId = authentication.getName();
        boolean isAdmin = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals("ROLE_ADMIN"));

        service.deleteParkingProfile(id, userId, isAdmin);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/analytics")
    @Operation(summary = "Get parking analytics")
    public ResponseEntity<Map<String, Object>> getParkingAnalytics(
            @PathVariable Long id,
            Authentication authentication) {

        String userId = authentication.getName();
        boolean isAdmin = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals("ROLE_ADMIN"));

        var profile = service.findById(id, userId, isAdmin)
                .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Parking profile not found"));

        Map<String, Object> analytics = new HashMap<>();
        analytics.put("parkingId", id);
        analytics.put("totalSpaces", profile.getTotalSpaces());
        analytics.put("occupiedSpaces", profile.getOccupiedSpaces());
        analytics.put("availableSpaces", profile.getTotalSpaces() - (profile.getOccupiedSpaces() != null ? profile.getOccupiedSpaces() : 0));
        analytics.put("occupancyRate", profile.getTotalSpaces() > 0
            ? (double) (profile.getOccupiedSpaces() != null ? profile.getOccupiedSpaces() : 0) / profile.getTotalSpaces() * 100
            : 0.0);
        analytics.put("accessibleSpaces", profile.getAccessibleSpaces());
        analytics.put("rating", profile.getRating());
        analytics.put("reviewCount", profile.getReviewCount());
        analytics.put("status", profile.getStatus());

        return ResponseEntity.ok(analytics);
    }

    // ========== Location Endpoints ==========

    @PostMapping("/{id}/location")
    @Operation(summary = "Create or update location for a parking profile")
    public ResponseEntity<ParkingProfileDto> createOrUpdateLocation(
            @PathVariable Long id,
            @Valid @RequestBody com.upc.quadrapp.parkingmanagement.domain.model.valueobjects.LocationData location,
            Authentication authentication) {

        String userId = authentication.getName();
        boolean isAdmin = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals("ROLE_ADMIN"));

        var updated = service.updateLocation(id, location, userId, isAdmin);
        return ResponseEntity.ok(mapper.toDto(updated));
    }

    @PutMapping("/{id}/location")
    @Operation(summary = "Update location for a parking profile")
    public ResponseEntity<ParkingProfileDto> updateLocation(
            @PathVariable Long id,
            @Valid @RequestBody com.upc.quadrapp.parkingmanagement.domain.model.valueobjects.LocationData location,
            Authentication authentication) {

        String userId = authentication.getName();
        boolean isAdmin = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals("ROLE_ADMIN"));

        var updated = service.updateLocation(id, location, userId, isAdmin);
        return ResponseEntity.ok(mapper.toDto(updated));
    }

    // ========== Pricing Endpoints ==========

    @PostMapping("/{id}/pricing")
    @Operation(summary = "Create or update pricing for a parking profile")
    public ResponseEntity<ParkingProfileDto> createOrUpdatePricing(
            @PathVariable Long id,
            @Valid @RequestBody com.upc.quadrapp.parkingmanagement.domain.model.valueobjects.PricingData pricing,
            Authentication authentication) {

        String userId = authentication.getName();
        boolean isAdmin = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals("ROLE_ADMIN"));

        var updated = service.updatePricing(id, pricing, userId, isAdmin);
        return ResponseEntity.ok(mapper.toDto(updated));
    }

    @PutMapping("/{id}/pricing")
    @Operation(summary = "Update pricing for a parking profile")
    public ResponseEntity<ParkingProfileDto> updatePricing(
            @PathVariable Long id,
            @Valid @RequestBody com.upc.quadrapp.parkingmanagement.domain.model.valueobjects.PricingData pricing,
            Authentication authentication) {

        String userId = authentication.getName();
        boolean isAdmin = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals("ROLE_ADMIN"));

        var updated = service.updatePricing(id, pricing, userId, isAdmin);
        return ResponseEntity.ok(mapper.toDto(updated));
    }

    // ========== Features Endpoints ==========

    @PostMapping("/{id}/features")
    @Operation(summary = "Create or update features for a parking profile")
    public ResponseEntity<ParkingProfileDto> createOrUpdateFeatures(
            @PathVariable Long id,
            @Valid @RequestBody com.upc.quadrapp.parkingmanagement.domain.model.valueobjects.FeaturesData features,
            Authentication authentication) {

        String userId = authentication.getName();
        boolean isAdmin = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals("ROLE_ADMIN"));

        var updated = service.updateFeatures(id, features, userId, isAdmin);
        return ResponseEntity.ok(mapper.toDto(updated));
    }

    @PutMapping("/{id}/features")
    @Operation(summary = "Update features for a parking profile")
    public ResponseEntity<ParkingProfileDto> updateFeatures(
            @PathVariable Long id,
            @Valid @RequestBody com.upc.quadrapp.parkingmanagement.domain.model.valueobjects.FeaturesData features,
            Authentication authentication) {

        String userId = authentication.getName();
        boolean isAdmin = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals("ROLE_ADMIN"));

        var updated = service.updateFeatures(id, features, userId, isAdmin);
        return ResponseEntity.ok(mapper.toDto(updated));
    }

    // Nested endpoints for location, pricing, and features
    @GetMapping("/{id}/location")
    @Operation(summary = "Get location for parking profile")
    public ResponseEntity<Map<String, Object>> getParkingLocation(
            @PathVariable Long id,
            Authentication authentication) {

        String userId = authentication.getName();
        boolean isAdmin = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals("ROLE_ADMIN"));

        System.out.println("=== GET LOCATION ===");
        System.out.println("Requested parking ID: " + id);
        System.out.println("User ID: " + userId);
        System.out.println("Is Admin: " + isAdmin);

        var profile = service.findById(id, userId, isAdmin)
                .orElseThrow(() -> {
                    System.out.println("❌ Parking not found with ID: " + id);
                    return new org.springframework.web.server.ResponseStatusException(
                            HttpStatus.NOT_FOUND, "Parking profile not found");
                });

        System.out.println("✅ Found parking: " + profile.getId() + " - " + profile.getName());
        System.out.println("Location is null? " + (profile.getLocation() == null));

        if (profile.getLocation() != null) {
            System.out.println("Location data: " + profile.getLocation().getCity());
        }

        Map<String, Object> response = new HashMap<>();
        response.put("data", profile.getLocation());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/pricing")
    @Operation(summary = "Get pricing for parking profile")
    public ResponseEntity<Map<String, Object>> getParkingPricing(
            @PathVariable Long id,
            Authentication authentication) {

        String userId = authentication.getName();
        boolean isAdmin = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals("ROLE_ADMIN"));

        System.out.println("=== GET PRICING ===");
        System.out.println("Requested parking ID: " + id);
        System.out.println("User ID: " + userId);

        var profile = service.findById(id, userId, isAdmin)
                .orElseThrow(() -> {
                    System.out.println("❌ Parking not found with ID: " + id);
                    return new org.springframework.web.server.ResponseStatusException(
                            HttpStatus.NOT_FOUND, "Parking profile not found");
                });

        System.out.println("✅ Found parking: " + profile.getId() + " - " + profile.getName());
        System.out.println("Pricing is null? " + (profile.getPricing() == null));

        Map<String, Object> response = new HashMap<>();
        response.put("data", profile.getPricing());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/features")
    @Operation(summary = "Get features for parking profile")
    public ResponseEntity<Map<String, Object>> getParkingFeatures(
            @PathVariable Long id,
            Authentication authentication) {

        String userId = authentication.getName();
        boolean isAdmin = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals("ROLE_ADMIN"));

        System.out.println("=== GET FEATURES ===");
        System.out.println("Requested parking ID: " + id);
        System.out.println("User ID: " + userId);

        var profile = service.findById(id, userId, isAdmin)
                .orElseThrow(() -> {
                    System.out.println("❌ Parking not found with ID: " + id);
                    return new org.springframework.web.server.ResponseStatusException(
                            HttpStatus.NOT_FOUND, "Parking profile not found");
                });

        System.out.println("✅ Found parking: " + profile.getId() + " - " + profile.getName());
        System.out.println("Features is null? " + (profile.getFeatures() == null));

        Map<String, Object> response = new HashMap<>();
        response.put("data", profile.getFeatures());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/initialize")
    @Operation(summary = "Initialize empty location, pricing and features for existing parking")
    public ResponseEntity<ParkingProfileDto> initializeParkingData(
            @PathVariable Long id,
            Authentication authentication) {

        String userId = authentication.getName();
        boolean isAdmin = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals("ROLE_ADMIN"));

        var profile = service.findById(id, userId, isAdmin)
                .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Parking profile not found"));

        System.out.println("=== INITIALIZING PARKING " + id + " ===");
        boolean needsUpdate = false;

        // Inicializar location si es null
        if (profile.getLocation() == null) {
            System.out.println("⚠️ Initializing empty location");
            var emptyLocation = new com.upc.quadrapp.parkingmanagement.domain.model.valueobjects.LocationData();
            profile = service.updateLocation(id, emptyLocation, userId, isAdmin);
            needsUpdate = true;
        }

        // Inicializar pricing si es null
        if (profile.getPricing() == null) {
            System.out.println("⚠️ Initializing empty pricing");
            var emptyPricing = new com.upc.quadrapp.parkingmanagement.domain.model.valueobjects.PricingData();
            profile = service.updatePricing(id, emptyPricing, userId, isAdmin);
            needsUpdate = true;
        }

        // Inicializar features si es null
        if (profile.getFeatures() == null) {
            System.out.println("⚠️ Initializing empty features");
            var emptyFeatures = new com.upc.quadrapp.parkingmanagement.domain.model.valueobjects.FeaturesData();
            profile = service.updateFeatures(id, emptyFeatures, userId, isAdmin);
            needsUpdate = true;
        }

        if (needsUpdate) {
            System.out.println("✅ Parking " + id + " initialized successfully");
        } else {
            System.out.println("ℹ️ Parking " + id + " already has location, pricing and features");
        }

        return ResponseEntity.ok(mapper.toDto(profile));
    }

    @GetMapping("/{id}/debug")
    @Operation(summary = "Debug endpoint - see full parking data including embedded objects")
    public ResponseEntity<Map<String, Object>> debugParkingData(
            @PathVariable Long id,
            Authentication authentication) {

        String userId = authentication.getName();
        boolean isAdmin = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals("ROLE_ADMIN"));

        var profile = service.findById(id, userId, isAdmin)
                .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Parking profile not found"));

        Map<String, Object> debug = new HashMap<>();
        debug.put("id", profile.getId());
        debug.put("name", profile.getName());
        debug.put("ownerId", profile.getOwnerId());

        // Location debug
        Map<String, Object> locationDebug = new HashMap<>();
        locationDebug.put("isNull", profile.getLocation() == null);
        if (profile.getLocation() != null) {
            locationDebug.put("addressLine", profile.getLocation().getAddressLine());
            locationDebug.put("city", profile.getLocation().getCity());
            locationDebug.put("postalCode", profile.getLocation().getPostalCode());
            locationDebug.put("latitude", profile.getLocation().getLatitude());
            locationDebug.put("longitude", profile.getLocation().getLongitude());
            locationDebug.put("hasAnyData",
                profile.getLocation().getAddressLine() != null ||
                profile.getLocation().getCity() != null ||
                profile.getLocation().getLatitude() != null
            );
        }
        debug.put("location", locationDebug);

        // Pricing debug
        Map<String, Object> pricingDebug = new HashMap<>();
        pricingDebug.put("isNull", profile.getPricing() == null);
        if (profile.getPricing() != null) {
            pricingDebug.put("hourlyRate", profile.getPricing().getHourlyRate());
            pricingDebug.put("dailyRate", profile.getPricing().getDailyRate());
            pricingDebug.put("monthlyRate", profile.getPricing().getMonthlyRate());
            pricingDebug.put("currency", profile.getPricing().getCurrency());
            pricingDebug.put("open24h", profile.getPricing().getOpen24h());
            pricingDebug.put("hasAnyData",
                profile.getPricing().getHourlyRate() != null ||
                profile.getPricing().getDailyRate() != null ||
                profile.getPricing().getCurrency() != null
            );
        }
        debug.put("pricing", pricingDebug);

        // Features debug
        Map<String, Object> featuresDebug = new HashMap<>();
        featuresDebug.put("isNull", profile.getFeatures() == null);
        if (profile.getFeatures() != null) {
            Map<String, Object> security = new HashMap<>();
            if (profile.getFeatures().getSecurity() != null) {
                security.put("security24h", profile.getFeatures().getSecurity().getSecurity24h());
                security.put("cameras", profile.getFeatures().getSecurity().getCameras());
            }
            featuresDebug.put("security", security);

            featuresDebug.put("hasAnyData",
                profile.getFeatures().getSecurity() != null ||
                profile.getFeatures().getAmenities() != null ||
                profile.getFeatures().getServices() != null ||
                profile.getFeatures().getPayments() != null
            );
        }
        debug.put("features", featuresDebug);

        System.out.println("=== DEBUG PARKING " + id + " ===");
        System.out.println("Location null? " + (profile.getLocation() == null));
        System.out.println("Pricing null? " + (profile.getPricing() == null));
        System.out.println("Features null? " + (profile.getFeatures() == null));

        return ResponseEntity.ok(debug);
    }

    @PostMapping("/initialize-all")
    @Operation(summary = "Initialize ALL parkings of the authenticated user")
    public ResponseEntity<Map<String, Object>> initializeAllUserParkings(
            Authentication authentication) {

        String userId = authentication.getName();
        boolean isAdmin = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals("ROLE_ADMIN"));

        System.out.println("=== INITIALIZING ALL PARKINGS FOR USER: " + userId + " ===");

        List<ParkingProfileDto> profiles = service.listParkingProfiles(userId, isAdmin, null)
                .stream()
                .map(profile -> {
                    System.out.println("Processing parking ID: " + profile.getId() + " - " + profile.getName());

                    boolean updated = false;

                    // Inicializar location si es null
                    if (profile.getLocation() == null) {
                        System.out.println("  ⚠️ Initializing location for parking " + profile.getId());
                        var emptyLocation = new com.upc.quadrapp.parkingmanagement.domain.model.valueobjects.LocationData();
                        profile = service.updateLocation(profile.getId(), emptyLocation, userId, isAdmin);
                        updated = true;
                    }

                    // Inicializar pricing si es null
                    if (profile.getPricing() == null) {
                        System.out.println("  ⚠️ Initializing pricing for parking " + profile.getId());
                        var emptyPricing = new com.upc.quadrapp.parkingmanagement.domain.model.valueobjects.PricingData();
                        profile = service.updatePricing(profile.getId(), emptyPricing, userId, isAdmin);
                        updated = true;
                    }

                    // Inicializar features si es null
                    if (profile.getFeatures() == null) {
                        System.out.println("  ⚠️ Initializing features for parking " + profile.getId());
                        var emptyFeatures = new com.upc.quadrapp.parkingmanagement.domain.model.valueobjects.FeaturesData();
                        profile = service.updateFeatures(profile.getId(), emptyFeatures, userId, isAdmin);
                        updated = true;
                    }

                    if (updated) {
                        System.out.println("  ✅ Parking " + profile.getId() + " initialized");
                    } else {
                        System.out.println("  ℹ️ Parking " + profile.getId() + " already initialized");
                    }

                    return profile;
                })
                .map(mapper::toDto)
                .toList();

        Map<String, Object> response = new HashMap<>();
        response.put("message", "All parkings initialized successfully");
        response.put("totalParkings", profiles.size());
        response.put("parkings", profiles);

        System.out.println("=== INITIALIZATION COMPLETE: " + profiles.size() + " parkings processed ===");

        return ResponseEntity.ok(response);
    }
}
