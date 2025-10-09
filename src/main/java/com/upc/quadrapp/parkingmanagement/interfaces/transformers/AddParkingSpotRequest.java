package com.upc.quadrapp.parkingmanagement.interfaces.transformers;

public class AddParkingSpotRequest {
    private Integer row;
    private Integer column;
    private String label;

    public AddParkingSpotRequest() {}

    public AddParkingSpotRequest(Integer row, Integer column, String label) {
        this.row = row;
        this.column = column;
        this.label = label;
    }

    public Integer getRow() { return row; }
    public void setRow(Integer row) { this.row = row; }

    public Integer getColumn() { return column; }
    public void setColumn(Integer column) { this.column = column; }

    public String getLabel() { return label; }
    public void setLabel(String label) { this.label = label; }
}
