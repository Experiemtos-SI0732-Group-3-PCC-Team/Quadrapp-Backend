package com.upc.quadrapp.parkingmanagement.domain.model.aggregates;

import java.util.Objects;
import java.util.UUID;

public class ParkingSpot {
    private final UUID id;
    private final Long parkingId;
    private boolean available;
    private final Integer rowIndex;
    private final Integer columnIndex;
    private final String label;

    public ParkingSpot(Long parkingId, Integer rowIndex, Integer columnIndex, String label) {
        this.id = UUID.randomUUID();
        this.parkingId = parkingId;
        this.available = true;
        this.rowIndex = rowIndex;
        this.columnIndex = columnIndex;
        this.label = label;
    }

    public UUID getId() { return id; }
    public Long getParkingId() { return parkingId; }
    public boolean isAvailable() { return available; }
    public Integer getRowIndex() { return rowIndex; }
    public Integer getColumnIndex() { return columnIndex; }
    public String getLabel() { return label; }

    public void setAvailability(boolean state) {
        this.available = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ParkingSpot)) return false;
        ParkingSpot that = (ParkingSpot) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }
}
