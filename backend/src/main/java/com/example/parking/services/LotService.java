package com.example.parking.services;

import com.example.parking.dao.ParkingLotDAO;
import com.example.parking.dao.ParkingSpotDAO;
import com.example.parking.dao.ReservationsDAO;
import com.example.parking.dto.ParkingLotDTO;
import com.example.parking.dto.ParkingSpotDTO;
import com.example.parking.dto.ReservationDTO;
import com.example.parking.entities.Report;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LotService {

    private ParkingLotDAO parkingLotDAO;
    private ParkingSpotDAO parkingSpotDAO;
    private ReservationsDAO reservationDAO;

    public LotService(ParkingLotDAO parkingLotDAO, ParkingSpotDAO parkingSpotDAO, ReservationsDAO reservationsDAO) {
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

    public List<Report> collectStatistics(int lotId) throws JRException, FileNotFoundException {
//        Occupancies and Revenues
        List<ParkingSpotDTO> spots = parkingSpotDAO.getSpotsByLotId(lotId);
        long numberOfSpots = spots.size();
        double lastDayOccupancy, lastMonthOccupancy, lastYearOccupancy;
        long lastDayUsedSpotsCount=0, lastMonthUsedSpotsCount=0, lastYearUsedSpotsCount=0;
        long lastDayRevenue=0, lastMonthRevenue=0, lastYearRevenue=0;
        int lastDayViolations=0, lastMonthViolations=0, lastYearViolations=0;

        boolean isCurrentSpotDone = false;
        for (ParkingSpotDTO spot : spots) {
            isCurrentSpotDone = false;

//            Information about the spot for only the current day
            int number = reservationDAO.numberOfReservations(spot.getSpot_id(), 1);
            if (number > 0) {
                isCurrentSpotDone = true;
                lastDayUsedSpotsCount++;
                lastMonthUsedSpotsCount++;
                lastYearUsedSpotsCount++;
            }
            lastDayRevenue +=  number * spot.getPrice();

//            Information about the spot for only the current month
            number = reservationDAO.numberOfReservations(spot.getSpot_id(), 2);
            if (number > 0 && !isCurrentSpotDone) {
                isCurrentSpotDone = true;
                lastMonthUsedSpotsCount++;
                lastYearUsedSpotsCount++;
            }
            lastMonthRevenue += number * spot.getPrice();

//            Information about the spot for only the current month
            number = reservationDAO.numberOfReservations(spot.getSpot_id(), 3);
            if (number > 0 && !isCurrentSpotDone) {
                lastYearUsedSpotsCount++;
            }
            lastYearRevenue += number * spot.getPrice();

            HashMap<String, Double> day = reservationDAO.getNumberAndAmountOfViolations(spot.getSpot_id(), 1);
            HashMap<String, Double> month = reservationDAO.getNumberAndAmountOfViolations(spot.getSpot_id(), 2);
            HashMap<String, Double> year = reservationDAO.getNumberAndAmountOfViolations(spot.getSpot_id(), 3);

            lastDayRevenue += day.get("penalties");
            lastMonthRevenue += month.get("penalties");
            lastYearRevenue += year.get("penalties");

            lastDayViolations += day.get("violations");
            lastMonthViolations += month.get("violations");
            lastYearViolations += year.get("violations");
        }

        lastDayOccupancy = (double)lastDayUsedSpotsCount/(double)numberOfSpots;
        lastMonthOccupancy = (double)lastMonthUsedSpotsCount/(double)numberOfSpots;
        lastYearOccupancy = (double)lastYearUsedSpotsCount/(double)numberOfSpots;

        List<Report> reports = new ArrayList<>();
        reports.add(new Report("Day", lastDayOccupancy*100, lastDayRevenue, lastDayViolations));
        reports.add(new Report("Month", lastMonthOccupancy*100, lastMonthRevenue, lastMonthViolations));
        reports.add(new Report("Year", lastYearOccupancy*100, lastYearRevenue, lastYearViolations));

//        return generateReport(reports);
        return reports;
    }

    public ResponseEntity<FileSystemResource> generateReport(List<Report> reports) throws FileNotFoundException, JRException {

        Path path = Paths.get("src\\main\\resources\\LotAdmin.html");
        Path absolutePath = path.toAbsolutePath();
        Map<String, Object> params = new HashMap<>();
        params.put("Report Owner", "System Admin");
        File file = ResourceUtils.getFile("classpath:LotAdmin.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(reports);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSource);
        JasperExportManager.exportReportToHtmlFile(jasperPrint, absolutePath.toString());

        File sentFile = new File(absolutePath.toString());
        FileSystemResource fileResource = new FileSystemResource(sentFile);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "text/html");
        return ResponseEntity.ok()
                .headers(headers)
                .body(fileResource);
    }
}
