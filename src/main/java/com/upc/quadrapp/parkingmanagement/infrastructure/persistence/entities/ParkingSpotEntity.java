package com.upc.quadrapp.parkingmanagement.infrastructure.persistence.entities;

import jakarta.persistence.*;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "parking_spots")
public class ParkingSpotEntity {

    @Id
    @Column(columnDefinition = "BINARY(16)")
    @Setter
    private UUID id;

    @Setter
    private boolean available;
    @Setter
    private Integer rowIndex;
    @Setter
    private Integer columnIndex;
    @Setter
    private String label;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parking_id")
    private ParkingEntity parking;

    public ParkingSpotEntity() { }

    public UUID getId() { return id; }

    public boolean isAvailable() { return available; }

    public Integer getRowIndex() { return rowIndex; }

    public Integer getColumnIndex() { return columnIndex; }

    public String getLabel() { return label; }

    public ParkingEntity getParking() { return parking; }
}
