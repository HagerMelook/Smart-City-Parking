package com.example.parking.services;

import com.example.parking.dao.ParkingLotDAO;
import com.example.parking.dao.ParkingSpotDAO;
import com.example.parking.dao.ReservationsDAO;
import com.example.parking.dto.ParkingLotDTO;
import com.example.parking.dto.ParkingSpotDTO;
import com.example.parking.dto.ReservationDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashboardService {

    ParkingLotDAO parkingLotDAO;
    ParkingSpotDAO parkingSpotDAO;
    ReservationsDAO reservationsDAO;

    public DashboardService(ParkingLotDAO parkingLotDAO, ParkingSpotDAO parkingSpotDAO, ReservationsDAO reservationsDAO) {
        this.parkingLotDAO = parkingLotDAO;
        this.parkingSpotDAO = parkingSpotDAO;
        this.reservationsDAO = reservationsDAO;
    }

    public String collectStatistics(int lotId) {
//        Occupancies and Revenues
        List<ParkingSpotDTO> spots = parkingSpotDAO.getSpotsByLotId(lotId);
        long numberOfSpots = spots.size();
        double lastDayOccupancy, lastMonthOccupancy, lastYearOccupancy;
        long lastDayUsedSpotsCount=0, lastMonthUsedSpotsCount=0, lastYearUsedSpotsCount=0;
        long lastDayRevenue=0, lastMonthRevenue=0, lastYearRevenue=0;

        boolean isCurrentSpotDone = false;
        for (ParkingSpotDTO spot : spots) {
            isCurrentSpotDone = false;

//            Information about the spot for only the current day
            String[] statistics = reservationsDAO.numberOfReservationsAndRevenueForSpotLastInterval(spot.getSpot_id(), 1).split(" ");
            int n = Integer.parseInt(statistics[0]);
            int penalties = Integer.parseInt(statistics[1]);

            if (n > 0) {
                isCurrentSpotDone = true;
                lastDayUsedSpotsCount++;
                lastMonthUsedSpotsCount++;
                lastYearUsedSpotsCount++;
            }
            double revenue =  penalties + n*spot.getPrice();
            lastDayRevenue+= revenue;

//            Information about the spot for only the current month
            statistics = reservationsDAO.numberOfReservationsAndRevenueForSpotLastInterval(spot.getSpot_id(), 2).split(" ");
            n = Integer.parseInt(statistics[0]);
            penalties = Integer.parseInt(statistics[1]);

            if (n > 0 && !isCurrentSpotDone) {
                isCurrentSpotDone = true;
                lastMonthUsedSpotsCount++;
                lastYearUsedSpotsCount++;
            }
            revenue =  penalties + n*spot.getPrice();
            lastMonthRevenue+= revenue;

//            Information about the spot for only the current month
            statistics = reservationsDAO.numberOfReservationsAndRevenueForSpotLastInterval(spot.getSpot_id(), 3).split(" ");
            n = Integer.parseInt(statistics[0]);
            penalties = Integer.parseInt(statistics[1]);
            if (n > 0 && !isCurrentSpotDone) {
                lastYearUsedSpotsCount++;
            }
            revenue =  penalties + n*spot.getPrice();
            lastYearRevenue+= revenue;
        }

        lastDayOccupancy = (double)lastDayUsedSpotsCount/(double)numberOfSpots;
        lastMonthOccupancy = (double)lastMonthUsedSpotsCount/(double)numberOfSpots;
        lastYearOccupancy = (double)lastYearUsedSpotsCount/(double)numberOfSpots;

        System.out.println(lastDayOccupancy*100+" "+lastMonthOccupancy*100+" "+lastYearOccupancy*100);
        System.out.println(lastDayRevenue+" "+lastMonthRevenue+" "+lastYearRevenue);
        return "rtvrt";
    }
}
