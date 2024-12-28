package com.example.parking.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TopLotsReport {
    String name;
    double revenue;
    int capacity;
}
