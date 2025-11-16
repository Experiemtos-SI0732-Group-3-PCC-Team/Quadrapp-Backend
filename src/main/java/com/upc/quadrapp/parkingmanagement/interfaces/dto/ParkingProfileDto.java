package com.upc.quadrapp.parkingmanagement.interfaces.dto;

import com.upc.quadrapp.parkingmanagement.domain.model.valueobjects.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParkingProfileDto {
    private Long id;
    private String name;
    private String ownerId;
    private String type; // ParkingType as string
    private String status; // ParkingStatus as string
    private String description;
    private LocationData location;
    private Integer totalSpaces;
    private Integer accessibleSpaces;
    private Integer occupiedSpaces;
    private String phone;
    private String email;
    private String website;
    private PricingData pricing;
    private FeaturesData features;
    private String imageUrl;
    private String openingHours;
    private Double rating;
    private Integer reviewCount;
    private Date createdAt;
    private Date updatedAt;
}

