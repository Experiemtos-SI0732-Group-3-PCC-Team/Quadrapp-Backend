package com.upc.quadrapp.parkingmanagement.infrastructure.persistence.entities;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "parking_spots")
public class ParkingSpotEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private boolean available;
    private Integer rowIndex;
    private Integer columnIndex;
    private String label;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parking_id")
    private ParkingEntity parking;

    public ParkingSpotEntity() { }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }

    public Integer getRowIndex() { return rowIndex; }
    public void setRowIndex(Integer rowIndex) { this.rowIndex = rowIndex; }

    public Integer getColumnIndex() { return columnIndex; }
    public void setColumnIndex(Integer columnIndex) { this.columnIndex = columnIndex; }

    public String getLabel() { return label; }
    public void setLabel(String label) { this.label = label; }

    public ParkingEntity getParking() { return parking; }
    public void setParking(ParkingEntity parking) { this.parking = parking; }
}
