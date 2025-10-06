package com.upc.quadrapp.parkingmanagement.domain.model.commands;

public class AddParkingSpotCommand {
    private final Long parkingId;
    private final int row;
    private final int column;
    private final String label;

    public AddParkingSpotCommand(Long parkingId, Integer row, Integer column, String label) {
        this.parkingId = parkingId;
        this.row = row;
        this.column = column;
        this.label = label;
    }

    public Long parkingId() { return parkingId; }
    public int row() { return row; }
    public int column() { return column; }
    public String label() { return label; }
}
