package com.example.parking.services;

import com.example.parking.dao.ReservationsDAO;
import com.example.parking.dto.ReservationDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class SpotService
{
    private final HttpSession httpSession;
    private ReservationsDAO reservationsDAO;

    public SpotService(ReservationsDAO reservationsDAO, HttpSession httpSession){
        this.reservationsDAO = reservationsDAO;
        this.httpSession = httpSession;
    }

    public ResponseEntity reserveSpot(int spotId, ReservationDTO reservations) {
        System.out.println("Receiving ");
        int id = reservationsDAO.insertResv(spotId, reservations);
        System.out.println("Reservation id is "+ id);
        if (id > 0) {
            return new ResponseEntity("Reserved", HttpStatus.OK);
        }
        else{
            return new ResponseEntity("Not Reserved", HttpStatus.BAD_REQUEST);
        }
    }
}
