package com.upc.quadrapp.parkingmanagement.infrastructure.persistence.entities;

import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "parkings")
public class ParkingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long ownerId;
    private String name;
    private String description;
    private String address;
    private double lat;
    private double lng;
    private float ratePerHour;
    private float rating;
    private Integer totalSpots;
    private Integer availableSpots;
    private Integer totalRows;
    private Integer totalColumns;
    private String imageUrl;

    @OneToMany(mappedBy = "parking", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ParkingSpotEntity> parkingSpots = new ArrayList<>();

    public ParkingEntity() { }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getOwnerId() { return ownerId; }
    public void setOwnerId(Long ownerId) { this.ownerId = ownerId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public double getLat() { return lat; }
    public void setLat(double lat) { this.lat = lat; }

    public double getLng() { return lng; }
    public void setLng(double lng) { this.lng = lng; }

    public float getRatePerHour() { return ratePerHour; }
    public void setRatePerHour(float ratePerHour) { this.ratePerHour = ratePerHour; }

    public float getRating() { return rating; }
    public void setRating(float rating) { this.rating = rating; }

    public Integer getTotalSpots() { return totalSpots; }
    public void setTotalSpots(Integer totalSpots) { this.totalSpots = totalSpots; }

    public Integer getAvailableSpots() { return availableSpots; }
    public void setAvailableSpots(Integer availableSpots) { this.availableSpots = availableSpots; }

    public Integer getTotalRows() { return totalRows; }
    public void setTotalRows(Integer totalRows) { this.totalRows = totalRows; }

    public Integer getTotalColumns() { return totalColumns; }
    public void setTotalColumns(Integer totalColumns) { this.totalColumns = totalColumns; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public List<ParkingSpotEntity> getParkingSpots() { return parkingSpots; }
    public void setParkingSpots(List<ParkingSpotEntity> parkingSpots) { this.parkingSpots = parkingSpots; }
}
