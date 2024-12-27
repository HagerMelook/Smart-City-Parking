package com.example.parking.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopUsersDTO {
    private int driver_id;
    private int number_of_resvs;

}
