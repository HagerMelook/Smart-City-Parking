package com.example.parking.services;

import com.example.parking.dao.ReservationsDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    @Autowired
    ReservationsDAO reservationsDAO;

    public void cancelReservation(int id) {
        reservationsDAO.updateResvStatus(id, "cancelled");
    }

    public void confirmReservation(int id) {
        reservationsDAO.updateResvStatus(id, "confirmed");
    }
}
