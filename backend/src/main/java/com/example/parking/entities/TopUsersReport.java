package com.example.parking.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TopUsersReport {
    String name;
    int numberOfReservations;
    String plateNumber;
}
