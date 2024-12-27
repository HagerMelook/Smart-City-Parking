package com.example.parking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParkingLotDTO {
    private int lot_id;
    private String name;
    private String location;
    private double latitude;
    private double longitude;
    private int capacity;

}
