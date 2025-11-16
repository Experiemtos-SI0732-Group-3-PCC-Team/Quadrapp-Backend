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
@RequestMapping(value = "/parkings", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Parking Profiles (Alias)", description = "Parking Profile Management - Alias Routes")
@SecurityRequirement(name = "bearerAuth")
public class ParkingAliasController {

    private final ParkingProfileService service;
    private final ParkingProfileMapper mapper;

    public ParkingAliasController(ParkingProfileService service, ParkingProfileMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    @Operation(summary = "List all parking profiles")
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

        // Devolver directamente el array, compatible con el frontend
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

        var entity = mapper.toEntity(dto);
        entity.setOwnerId(userId);

        var created = service.createParkingProfile(entity, userId);
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

        var entity = mapper.toEntity(dto);
        var updated = service.updateParkingProfile(id, entity, userId, isAdmin);

        return ResponseEntity.ok(mapper.toDto(updated));
    }

    @PatchMapping(value = "/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "Partially update parking profile")
    public ResponseEntity<ParkingProfileDto> patchParkingProfile(
            @PathVariable Long id,
            @RequestBody ParkingProfileDto dto,
            Authentication authentication) {

        String userId = authentication.getName();
        boolean isAdmin = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals("ROLE_ADMIN"));

        var entity = mapper.toEntity(dto);
        var updated = service.updateParkingProfile(id, entity, userId, isAdmin);

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
}
