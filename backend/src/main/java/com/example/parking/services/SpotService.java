package com.example.parking.services;

import com.example.parking.dao.ParkingSpotDAO;
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
    private ParkingSpotDAO parkingSpotDAO;

    public SpotService(ReservationsDAO reservationsDAO, HttpSession httpSession, ParkingSpotDAO parkingSpotDAO){
        this.reservationsDAO = reservationsDAO;
        this.httpSession = httpSession;
        this.parkingSpotDAO = parkingSpotDAO;
    }

    public ResponseEntity reserveSpot(int spotId, ReservationDTO reservation) {

        String result = parkingSpotDAO.updateSpotStatus(spotId, "reserved");
        if (result.startsWith("Update completed successfully.")) {
            int id = reservationsDAO.insertResv(spotId, reservation);
            System.out.println(id);
            if (id > 0) {
                return new ResponseEntity("Reserved", HttpStatus.OK);
            }
            else{
                return new ResponseEntity("Not Reserved", HttpStatus.BAD_REQUEST);
            }
        }
        else
            return new ResponseEntity("Not Reserved", HttpStatus.BAD_REQUEST);



    }
}
