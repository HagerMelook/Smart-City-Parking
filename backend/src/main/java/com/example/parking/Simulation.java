package com.example.parking;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.example.parking.dao.ParkingSpotDAO;
import com.example.parking.dao.ReservationsDAO;
import com.example.parking.dao.TransactionDAO;
import com.example.parking.dto.ParkingSpotDTO;
import com.example.parking.dto.ReservationDTO;

public class Simulation {
    public static class IoTDeviceRequest implements Runnable {
        private final int spotId;
        private final String status;
        public int resv_id = 1;
        public IoTDeviceRequest(int spotId, String status) {
            this.spotId = spotId;
            this.status = status;
        }

        @Override
        public void run() {
            ParkingSpotDAO parkingSpot = new ParkingSpotDAO();
            System.out.println("Thread started for Spot " + spotId + ", trying to set status to " + status);
            String result = parkingSpot.updateSpotStatus(spotId, status);
            System.out.println("In Spot " + spotId + " " + result);
            if (result.startsWith("Update completed successfully.") && "reserved".equals(status)) {
                ReservationDTO resv = new ReservationDTO();
                resv.setDriver_id(1);
                resv.setSpot_id(spotId);
                resv.setPenalty(800);
                Timestamp startTime = new Timestamp(System.currentTimeMillis());
                resv.setStart_time(startTime);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(startTime);
                calendar.add(Calendar.HOUR, 2);
                Timestamp endTime = new Timestamp(calendar.getTimeInMillis());
                resv.setEnd_time(endTime);
                ReservationsDAO reservationsDAO = new ReservationsDAO();
                int id = reservationsDAO.insertResv(spotId, resv);
                resv_id = id;
                System.out.println(id);
            }
            System.out.println("Thread completed for Spot " + spotId);
        }
    }

    public void SimConcurrentResv() {
        int spotId = 4;
        String status = "reserved";
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        // Simulate 2 concurrent IoT device requests for the same parking spot
        for (int i = 0; i < 2; i++) {
            executorService.submit(new IoTDeviceRequest(spotId, status));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        executorService.shutdown();
    }

    public void SimConcurrentOccuiped() {
        int spotId = 5;
        String status = "occupied";
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        // Simulate 2 concurrent IoT device requests for the same parking spot
        for (int i = 0; i < 2; i++) {
            executorService.submit(new IoTDeviceRequest(spotId, status));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        executorService.shutdown();
    }

    public void SimConcurrentOccuipedAndResv() {
        int spotId = 6;
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        // Simulate 2 concurrent IoT device requests for the same parking spot
        executorService.submit(new IoTDeviceRequest(spotId, "occupied"));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executorService.submit(new IoTDeviceRequest(spotId, "reserved"));
        executorService.shutdown();
    }

    public void SimSerialUpdates() {
        int spotId = 7;
        ExecutorService executorService = Executors.newFixedThreadPool(1);

        // Simulate 2 concurrent IoT device requests for the same parking spot
        IoTDeviceRequest iot = new IoTDeviceRequest(spotId, "reserved");
        executorService.submit(iot);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ReservationsDAO reservationsDAO = new ReservationsDAO();
        System.out.println("The reservation Id is "+iot.resv_id);
        reservationsDAO.updateResvStatus(iot.resv_id, "confirmed");
        ParkingSpotDAO parkingSpotDAO = new ParkingSpotDAO();
        ParkingSpotDTO parkingSpot = parkingSpotDAO.getSpotsById(spotId);
        TransactionDAO transactionDAO = new TransactionDAO();
        transactionDAO.insertTransaction(parkingSpot.getPrice(),1);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executorService.submit(new IoTDeviceRequest(spotId, "available"));
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executorService.submit(new IoTDeviceRequest(spotId, "occupied"));
        transactionDAO = new TransactionDAO();
        transactionDAO.insertTransaction(parkingSpot.getPrice(),1);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executorService.submit(new IoTDeviceRequest(spotId, "available"));
        executorService.shutdown();
    }

    public void SimResvThenCancelled() {
        int spotId = 9;
        ExecutorService executorService = Executors.newFixedThreadPool(1);

        // Simulate 2 concurrent IoT device requests for the same parking spot
        IoTDeviceRequest iot = new IoTDeviceRequest(spotId, "reserved");
        executorService.submit(iot);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ReservationsDAO reservationsDAO = new ReservationsDAO();
        System.out.println("The reservation Id is "+iot.resv_id);
        reservationsDAO.updateResvStatus(iot.resv_id, "cancelled");
        executorService.shutdown();
    }
}