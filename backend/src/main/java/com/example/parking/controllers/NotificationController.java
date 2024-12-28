package com.example.parking.controllers;

import com.example.parking.dao.NotificationDAO;
import com.example.parking.dto.NotificationDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/notifications")
public class NotificationController {
    NotificationDAO notificationDAO;

    public NotificationController(NotificationDAO notificationDAO) {
        this.notificationDAO = notificationDAO;
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<NotificationDTO>> getNotification(@PathVariable("id") int id) {
        List<NotificationDTO> notifications = notificationDAO.getNotificationByUserId(id);
        return ResponseEntity.ok(notifications);
    }

}
