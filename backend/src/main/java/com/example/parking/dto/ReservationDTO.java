package com.example.parking.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "EET")
    private Timestamp start_time;
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "EET")
    private Timestamp end_time;
    private double penalty;
    private String status;
}
