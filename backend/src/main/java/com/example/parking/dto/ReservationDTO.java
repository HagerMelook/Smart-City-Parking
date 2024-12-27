package com.example.parking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDTO {
    private int resv_id;
    private int driver_id;
    private int spot_id;
    private Timestamp start_time;
    private Timestamp end_time;
    private double penalty;
    private String status;
}
