package com.example.parking.controllers;

import com.example.parking.dao.ReservationsDAO;
import com.example.parking.dto.ReservationDTO;
import com.example.parking.services.ReservationService;
import jdk.jfr.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/reservations")
public class ReservationController {
    ReservationService reservationService;
    ReservationsDAO reservationsDAO;

    public ReservationController(ReservationsDAO reservationsDAO, ReservationService reservationService) {
        this.reservationsDAO = reservationsDAO;
        this.reservationService = reservationService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<ReservationDTO>> getNotification(@PathVariable("id") int id) {
        List<ReservationDTO> reservations = reservationsDAO.getResvsByDriverId(id);
        return ResponseEntity.ok(reservations);
    }

    @DeleteMapping("{id}/cancel")
    public void cancelReservation(@PathVariable int id) {
        reservationService.cancelReservation(id);
    }

    @PutMapping("{id}/confirm")
    public void confirmReservation(@PathVariable int id) {
        reservationService.confirmReservation(id);
    }
}
