package com.example.parking.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysAdminDTO {
    private int sys_admin_id;
    private String full_name;
    private String email;
    private String password;
}
