package com.example.parking.dto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO {
    private int transaction_id;
    private int driver_id;
    private double amount;
    private Timestamp timestamp;
    private String payment_status;
}
