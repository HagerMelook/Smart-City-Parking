package com.example.parking.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.example.parking.dto.ReservationDTO;
import com.example.parking.entities.DBConnection;
import com.example.parking.entities.Reservations;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationsDAO implements DBConnection {
    public int insertResv(int spot_id, ReservationDTO reservation) {
        String insertSQL = "INSERT INTO reservations (spot_id, driver_id, start_time, end_time) VALUES (?, ?, ?, ?)";
        int generatedId = 0;
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(insertSQL, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, spot_id);
            preparedStatement.setInt(2, reservation.getDriver_id());
            preparedStatement.setTimestamp(3, reservation.getStart_time());
            preparedStatement.setTimestamp(4, reservation.getEnd_time());
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Insert completed. Rows affected: " + rowsAffected);

            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        generatedId = generatedKeys.getInt(1);
                        System.out.println("Inserted row ID: " + generatedId);
                    } else {
                        System.out.println("No ID was returned.");
                    }
                }
            } else {
                System.out.println("Insert failed, no rows affected.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return generatedId;
    }

    public String updateResvStatus(int resv_id, String status) {
        String updateSQL = "UPDATE reservations SET status = ? WHERE resv_id = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {

            preparedStatement.setString(1, status);
            preparedStatement.setInt(2, resv_id);

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Update completed. Rows affected: " + rowsAffected);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Updated Completed Successfully";
    }

    public List<ReservationDTO> getResvsByStatus(String status) {
        String selectSQL = "SELECT * FROM reservations WHERE status= ?";
        ReservationDTO reservation = new ReservationDTO();
        List<ReservationDTO> list = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
            preparedStatement.setString(1, status);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    reservation = new ReservationDTO();
                    reservation.setResv_id(resultSet.getInt("resv_id"));
                    reservation.setSpot_id(resultSet.getInt("spot_id"));
                    reservation.setDriver_id(resultSet.getInt("driver_id"));
                    reservation.setStatus(status);
                    reservation.setStart_time(resultSet.getTimestamp("start_time"));
                    reservation.setEnd_time(resultSet.getTimestamp("end_time"));
                    reservation.setPenalty(resultSet.getDouble("penalty"));
                    list.add(reservation);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<ReservationDTO> getResvsByDriverId(int driver_id) {
        String selectSQL = "SELECT * FROM reservations WHERE driver_id= ?";
        ReservationDTO reservation = new ReservationDTO();
        List<ReservationDTO> list = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
            preparedStatement.setInt(1, driver_id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    reservation = new ReservationDTO();
                    reservation.setResv_id(resultSet.getInt("resv_id"));
                    reservation.setSpot_id(resultSet.getInt("spot_id"));
                    reservation.setDriver_id(driver_id);
                    reservation.setStatus(resultSet.getString("status"));
                    reservation.setStart_time(resultSet.getTimestamp("start_time"));
                    reservation.setEnd_time(resultSet.getTimestamp("end_time"));
                    reservation.setPenalty(resultSet.getDouble("penalty"));
                    list.add(reservation);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<ReservationDTO> getResvsBySpotId(int spot_id) {
        String selectSQL = "SELECT * FROM reservations WHERE spot_id= ?";
        ReservationDTO reservation = new ReservationDTO();
        List<ReservationDTO> list = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
            preparedStatement.setInt(1, spot_id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    reservation = new ReservationDTO();
                    if (resultSet.getString("status").equals("expired") || resultSet.getString("status").equals("cancelled")) {
                        continue;
                    }
                    reservation.setResv_id(resultSet.getInt("resv_id"));
                    reservation.setDriver_id(resultSet.getInt("driver_id"));
                    reservation.setSpot_id(spot_id);
                    reservation.setStatus(resultSet.getString("status"));
                    reservation.setStart_time(resultSet.getTimestamp("start_time"));
                    reservation.setEnd_time(resultSet.getTimestamp("end_time"));
                    reservation.setPenalty(resultSet.getDouble("penalty"));
                    list.add(reservation);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<ReservationDTO> getResvsByStartTime(Timestamp start_time) {
        String selectSQL = "SELECT * FROM reservations WHERE start_time>= ?";
        ReservationDTO reservation = new ReservationDTO();
        List<ReservationDTO> list = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
            preparedStatement.setTimestamp(1, start_time);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    reservation = new ReservationDTO();
                    reservation.setResv_id(resultSet.getInt("resv_id"));
                    reservation.setSpot_id(resultSet.getInt("spot_id"));
                    reservation.setDriver_id(resultSet.getInt("driver_id"));
                    reservation.setStatus(resultSet.getString("status"));
                    reservation.setStart_time(resultSet.getTimestamp("start_time"));
                    reservation.setEnd_time(resultSet.getTimestamp("end_time"));
                    reservation.setPenalty(resultSet.getDouble("penalty"));
                    list.add(reservation);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public ReservationDTO getResvsById(int resv_id) {
        String selectSQL = "SELECT * FROM reservations WHERE resv_id= ?";
        ReservationDTO reservation = new ReservationDTO();
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
            preparedStatement.setInt(1, resv_id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    reservation.setResv_id(resultSet.getInt("resv_id"));
                    reservation.setSpot_id(resultSet.getInt("spot_id"));
                    reservation.setDriver_id(resultSet.getInt("driver_id"));
                    reservation.setStatus(resultSet.getString("status"));
                    reservation.setStart_time(resultSet.getTimestamp("start_time"));
                    reservation.setEnd_time(resultSet.getTimestamp("end_time"));
                    reservation.setPenalty(resultSet.getDouble("penalty"));
                }else {
                    System.out.println("No reservation found with ID: " + resv_id);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservation;
    }

    public String numberOfReservationsAndRevenueForSpotLastInterval(int spotId, int index) {
//        Index is an indicator with 1 for day, 2 for month, 3 for year
        String selectSQL = "SELECT COUNT(*) as reserved, SUM(penalty) as penalties FROM reservations " +
                "WHERE spot_id= ? AND status <> 'cancelled' AND YEAR(start_time) = YEAR(CURDATE()) ";
        switch (index){
            case 1:
                selectSQL += "AND DAY(start_time) = DAY(CURDATE()) AND MONTH(start_time) = MONTH(CURDATE())";
                break;
            case 2:
                selectSQL += "AND MONTH(start_time) = MONTH(CURDATE()) ";
                break;
            default:
                break;
        }

        int count = 0;
        long penalties=0;
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
            preparedStatement.setInt(1, spotId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    count = resultSet.getInt("reserved");
                    penalties = resultSet.getInt("penalties");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return (count+" "+penalties);
    }

}