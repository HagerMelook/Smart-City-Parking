package com.example.parking.services;

import com.example.parking.dao.ParkingLotDAO;
import com.example.parking.dao.ParkingSpotDAO;
import com.example.parking.dao.ReservationsDAO;
import com.example.parking.dto.ParkingLotDTO;
import com.example.parking.dto.ParkingSpotDTO;
import com.example.parking.dto.ReservationDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LotsService {

    private ParkingLotDAO parkingLotDAO;
    private ParkingSpotDAO parkingSpotDAO;
    private ReservationsDAO reservationDAO;

    public LotsService (ParkingLotDAO parkingLotDAO, ParkingSpotDAO parkingSpotDAO, ReservationsDAO reservationsDAO) {
        this.parkingLotDAO = parkingLotDAO;
        this.parkingSpotDAO = parkingSpotDAO;
        this.reservationDAO = reservationsDAO;
    }

    public List<ParkingLotDTO> retrieveAll() {
        List<ParkingLotDTO> lots = parkingLotDAO.getAllLots();
        for (ParkingLotDTO lot : lots) {
            String[] coordinates = lot.getLocation().replace("POINT(", "").replace(")", "").split(" ");
            lot.setLatitude(Double.parseDouble(coordinates[0]));
            lot.setLongitude(Double.parseDouble(coordinates[1]));
        }
        return lots;
    }

    public List<ParkingSpotDTO> retrieveLotSpot(int id) {
        List<ParkingSpotDTO> spots = parkingSpotDAO.getSpotsByLotId(id);
        for (ParkingSpotDTO spot : spots) {
            List<ReservationDTO> reservations = reservationDAO.getResvsBySpotId(spot.getSpot_id());
            extractAvailableIntervals(spot, reservations);
        }
        return spots;
    }

    private void extractAvailableIntervals(ParkingSpotDTO spot, List<ReservationDTO> reservations) {
        List<int[]> intervals = new ArrayList<>();
        intervals.add(new int[]{0, 24});
        for (ReservationDTO reservation : reservations) {
            int from = reservation.getStart_time().getHours();
            int to = reservation.getEnd_time().getHours();
            for (int[] interval : intervals) {
                if (interval[0] <= from && interval[1] >= to) {
                    intervals.add(new int[]{interval[0], from});
                    intervals.add(new int[]{to, interval[1]});
                    intervals.remove(interval);
                    break;
                }
            }
        }
        spot.setAvailableHours(intervals);
    }

    public ResponseEntity appendNewLot(ParkingLotDTO lot) {
        lot.setLocation(lot.getLatitude() +", "+lot.getLongitude());
        int insertedId = parkingLotDAO.insertParkingLot(lot);
        if (insertedId > 0) {
            return new ResponseEntity("Lot is Successfully Inserted.", HttpStatus.OK);
        }
        else{
            return new ResponseEntity("An Error Occurred.", HttpStatus.BAD_REQUEST);
        }
    }
}
