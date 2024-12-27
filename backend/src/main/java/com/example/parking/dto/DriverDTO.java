package com.example.parking.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DriverDTO {
    private int id;
    private String full_name;
    private String license_plate;
    private String email;
    private String password;
    private String card_type;
    private int card_num;
    private int cvv;
    private Date expiry_date;
}
