package com.example.parking.dto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDTO {
    private int notification_id;
    private int user_id;
    private String type;
    private String msg;
    private Timestamp time;
}
