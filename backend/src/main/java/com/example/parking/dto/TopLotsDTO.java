package com.example.parking.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopLotsDTO {
    private int lot_id;
    private double revenue;
}
