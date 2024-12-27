package com.example.parking.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LotAdminDTO {
    private int lot_admin_id;
    private int lot_id;
    private String full_name;
    private String email;
    private String password;
}
