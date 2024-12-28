package com.example.parking.controllers;

import com.example.parking.services.ReservationService;
import jdk.jfr.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/reservations")
public class ReservationController {

    @Autowired
    ReservationService reservationService;

    @DeleteMapping("{id}/cancel")
    public void cancelReservation(@PathVariable int id) {
        reservationService.cancelReservation(id);
    }

    @PutMapping("{id}/confirm")
    public void confirmReservation(@PathVariable int id) {
        reservationService.confirmReservation(id);
    }
}
