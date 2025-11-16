package com.upc.quadrapp.parkingmanagement.interfaces.controllers;

import com.upc.quadrapp.parkingmanagement.application.services.ParkingProfileService;
import com.upc.quadrapp.parkingmanagement.domain.model.valueobjects.LocationData;
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
import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1/locations", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Locations", description = "Location Management Endpoints")
@SecurityRequirement(name = "bearerAuth")
public class LocationController {

    private final ParkingProfileService service;
    private final ParkingProfileMapper mapper;

    public LocationController(ParkingProfileService service, ParkingProfileMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    @Operation(summary = "Get location by profileId", description = "Returns location data for a parking profile")
    public ResponseEntity<Map<String, Object>> getLocation(
            @RequestParam(required = false) Long profileId,
            @RequestParam(required = false) Long parkingId,
            Authentication authentication) {

        String userId = authentication.getName();
        boolean isAdmin = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals("ROLE_ADMIN"));

        Long id = profileId != null ? profileId : parkingId;
        if (id == null) {
            throw new org.springframework.web.server.ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "profileId or parkingId is required");
        }

        var profile = service.findById(id, userId, isAdmin)
                .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Parking profile not found"));

        Map<String, Object> response = new HashMap<>();
        response.put("data", profile.getLocation());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get location by parking profile ID")
    public ResponseEntity<Map<String, Object>> getLocationById(
            @PathVariable Long id,
            Authentication authentication) {

        String userId = authentication.getName();
        boolean isAdmin = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals("ROLE_ADMIN"));

        var profile = service.findById(id, userId, isAdmin)
                .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Parking profile not found"));

        Map<String, Object> response = new HashMap<>();
        response.put("data", profile.getLocation());
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Operation(summary = "Create or update location for a parking profile",
               description = "Requires parkingId or profileId in request body")
    public ResponseEntity<Map<String, Object>> createLocation(
            @Valid @RequestBody LocationRequest request,
            Authentication authentication) {

        String userId = authentication.getName();
        boolean isAdmin = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals("ROLE_ADMIN"));

        Long parkingId = request.parkingId != null ? request.parkingId : request.profileId;
        if (parkingId == null) {
            throw new org.springframework.web.server.ResponseStatusException(
                HttpStatus.BAD_REQUEST, "parkingId or profileId is required");
        }

        var updated = service.updateLocation(parkingId, request.toLocationData(), userId, isAdmin);

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(Map.of("profile", mapper.toDto(updated)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update location by ID (parking profile ID)")
    public ResponseEntity<Map<String, Object>> updateLocation(
            @PathVariable Long id,
            @Valid @RequestBody LocationData location,
            Authentication authentication) {

        String userId = authentication.getName();
        boolean isAdmin = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals("ROLE_ADMIN"));

        var updated = service.updateLocation(id, location, userId, isAdmin);

        return ResponseEntity.ok(Map.of("profile", mapper.toDto(updated)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete location by ID (sets location to null)")
    public ResponseEntity<Void> deleteLocation(
            @PathVariable Long id,
            Authentication authentication) {

        String userId = authentication.getName();
        boolean isAdmin = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals("ROLE_ADMIN"));

        service.updateLocation(id, null, userId, isAdmin);
        return ResponseEntity.noContent().build();
    }

    // Request DTO for POST with parkingId/profileId
    public static class LocationRequest extends LocationData {
        public Long parkingId;
        public Long profileId;

        public LocationData toLocationData() {
            return new LocationData(
                getAddressLine(), getCity(), getPostalCode(),
                getState(), getCountry(), getLatitude(), getLongitude()
            );
        }
    }
}
