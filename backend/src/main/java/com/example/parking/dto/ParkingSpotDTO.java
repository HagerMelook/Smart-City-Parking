package com.example.parking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParkingSpotDTO {
    private int spot_id;
    private int lot_id;
    private String status;
    private String type;
    private Double price;
    private List<int[]> availableHours;
}
