package com.example.parking.controllers;

import com.example.parking.dao.ReservationsDAO;
import com.example.parking.dto.ReservationDTO;
import com.example.parking.services.SpotService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/spots")
public class SpotController {

    private SpotService spotService;

    public SpotController(SpotService spotService) {
        this.spotService = spotService;
    }

    @PostMapping("/{spotId}/reserve")
    public ResponseEntity reserveSpot(@PathVariable int spotId, @RequestBody ReservationDTO reservations) {
        System.out.println("Receiving");
        return spotService.reserveSpot(spotId, reservations);
    }

}
