package com.upc.quadrapp.parkingmanagement.interfaces.controllers;

import com.upc.quadrapp.parkingmanagement.application.services.ParkingProfileService;
import com.upc.quadrapp.parkingmanagement.domain.model.valueobjects.PricingData;
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
@RequestMapping(value = "/api/v1/pricing", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Pricing", description = "Pricing Management Endpoints")
@SecurityRequirement(name = "bearerAuth")
public class PricingController {

    private final ParkingProfileService service;
    private final ParkingProfileMapper mapper;

    public PricingController(ParkingProfileService service, ParkingProfileMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    @Operation(summary = "Get pricing by profileId", description = "Returns pricing data for a parking profile")
    public ResponseEntity<Map<String, Object>> getPricing(
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
        response.put("data", profile.getPricing());
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Operation(summary = "Create or update pricing for a parking profile",
            description = "Requires parkingId or profileId in request body")
    public ResponseEntity<Map<String, Object>> createPricing(
            @Valid @RequestBody PricingRequest request,
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

        var updated = service.updatePricing(parkingId, request.toPricingData(), userId, isAdmin);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("profile", mapper.toDto(updated)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update pricing by ID (parking profile ID)")
    public ResponseEntity<Map<String, Object>> updatePricing(
            @PathVariable Long id,
            @Valid @RequestBody PricingData pricing,
            Authentication authentication) {

        String userId = authentication.getName();
        boolean isAdmin = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals("ROLE_ADMIN"));

        var updated = service.updatePricing(id, pricing, userId, isAdmin);

        return ResponseEntity.ok(Map.of("profile", mapper.toDto(updated)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete pricing by ID (sets pricing to null)")
    public ResponseEntity<Void> deletePricing(
            @PathVariable Long id,
            Authentication authentication) {

        String userId = authentication.getName();
        boolean isAdmin = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals("ROLE_ADMIN"));

        service.updatePricing(id, null, userId, isAdmin);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get pricing by parking profile ID")
    public ResponseEntity<Map<String, Object>> getPricingById(
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
        response.put("data", profile.getPricing());
        return ResponseEntity.ok(response);
    }

    // Request DTO for POST with parkingId/profileId
    public static class PricingRequest extends PricingData {
        public Long parkingId;
        public Long profileId;

        public PricingData toPricingData() {
            PricingData data = new PricingData();
            data.setHourlyRate(getHourlyRate());
            data.setDailyRate(getDailyRate());
            data.setMonthlyRate(getMonthlyRate());
            data.setCurrency(getCurrency());
            data.setMinimumStay(getMinimumStay());
            data.setOpen24h(getOpen24h());
            data.setOperatingHours(getOperatingHours());
            data.setOperatingDays(getOperatingDays());
            data.setPromotions(getPromotions());
            return data;
        }
    }
}
