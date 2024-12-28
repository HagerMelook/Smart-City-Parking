package com.example.parking.controllers;

import com.example.parking.dao.ReservationsDAO;
import com.example.parking.dto.ReservationDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/reservation")
public class ReservationController {
    ReservationsDAO reservationsDAO;

    public ReservationController(ReservationsDAO reservationsDAO) {
        this.reservationsDAO = reservationsDAO;
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<ReservationDTO>> getNotification(@PathVariable("id") int id) {
        List<ReservationDTO> reservations = reservationsDAO.getResvsByDriverId(id);
        return ResponseEntity.ok(reservations);
    }
}
