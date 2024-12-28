package com.example.parking.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Report {

    String interval;
    double occupancyRate;
    double revenue;
    int violations;

}
