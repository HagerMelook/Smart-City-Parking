package com.example.parking.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.parking.dto.ParkingSpotDTO;
import com.example.parking.entities.DBConnection;

@Repository
public class ParkingSpotDAO implements DBConnection {
    // public int insertSpot(int lot_id, String type) {
    //     String insertSQL = "INSERT INTO parking_spots (lot_id, type) VALUES (?, ?)";
    //     int generatedId = 0;
    //     try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
    //             PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {

    //         preparedStatement.setInt(1, lot_id);
    //         preparedStatement.setString(2, type);
    //         int rowsAffected = preparedStatement.executeUpdate();
    //         System.out.println("Insert completed. Rows affected: " + rowsAffected);

    //         if (rowsAffected > 0) {
    //             try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
    //                 if (generatedKeys.next()) {
    //                     generatedId = generatedKeys.getInt("spot_id");
    //                     System.out.println("Inserted row ID: " + generatedId);
    //                 } else {
    //                     System.out.println("No ID was returned.");
    //                 }
    //             }
    //         } else {
    //             System.out.println("Insert failed, no rows affected.");
    //         }
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //     }
    //     return generatedId;
    // }

    public String updateSpotStatus(int spot_id, String status) {
        String selectForUpdateSQL = "SELECT status FROM parking_spots WHERE spot_id = ? FOR UPDATE";
        String updateSQL = "UPDATE parking_spots SET status = ? WHERE spot_id = ?";
        String msg;
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            connection.setAutoCommit(false);
            try (PreparedStatement selectStmt = connection.prepareStatement(selectForUpdateSQL)) {
                selectStmt.setInt(1, spot_id);
                ResultSet resultSet = selectStmt.executeQuery();
                if (resultSet.next()) {
                    String currentStatus = resultSet.getString("status");
                    if ("available".equals(currentStatus)) {
                        try (PreparedStatement updateStmt = connection.prepareStatement(updateSQL)) {
                            updateStmt.setString(1, status);
                            updateStmt.setInt(2, spot_id);
                            int rowsAffected = updateStmt.executeUpdate();
                            if (rowsAffected > 0) {
                                connection.commit();
                                msg = "Update completed successfully. Rows affected: " + rowsAffected;
                            } else {
                                connection.rollback();
                                msg = "Update failed. No rows were affected.";
                            }
                        }
                    } else {
                        connection.rollback();
                        msg = "Update denied. Current status is not Available.";
                    }
                } else {
                    connection.rollback();
                    msg = "Spot not found. Update failed.";
                }
            }
        } catch (SQLException e) {
            msg = "An error occurred: " + e.getMessage();
            e.printStackTrace();
        }
        return msg;
    }
    

    public String updateSpotType(int spotId, String type) {
        String updateSQL = "UPDATE parking_spots SET type = ? WHERE spot_id = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {

            preparedStatement.setString(1, type);
            preparedStatement.setInt(2, spotId);

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Update completed. Rows affected: " + rowsAffected);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Updated Completed Successfully";
    }

    public String updateSpotPrice(int spotId, Double price) {
        String updateSQL = "UPDATE parking_spots SET price = ? WHERE spot_id = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {

            preparedStatement.setDouble(1, price);
            preparedStatement.setInt(2, spotId);

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Update completed. Rows affected: " + rowsAffected);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Updated Completed Successfully";
    }

    public List<ParkingSpotDTO> getSpotsByStatus(String status) {
        String selectSQL = "SELECT * FROM parking_spots WHERE status= ?";
        ParkingSpotDTO parkingSpot = new ParkingSpotDTO();
        List<ParkingSpotDTO> list = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
            preparedStatement.setString(1, status);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    parkingSpot = new ParkingSpotDTO();
                    parkingSpot.setSpot_id(resultSet.getInt("spot_id"));
                    parkingSpot.setLot_id(resultSet.getInt("lot_id"));
                    parkingSpot.setStatus(status);
                    parkingSpot.setType(resultSet.getString("type"));
                    parkingSpot.setPrice(resultSet.getDouble("price"));
                    list.add(parkingSpot);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<ParkingSpotDTO> getSpotsByType(String type) {
        String selectSQL = "SELECT * FROM parking_spots WHERE type = ?";
        ParkingSpotDTO parkingSpot = new ParkingSpotDTO();
        List<ParkingSpotDTO> list = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
            preparedStatement.setString(1, type);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    parkingSpot = new ParkingSpotDTO();
                    parkingSpot.setSpot_id(resultSet.getInt("spot_id"));
                    parkingSpot.setLot_id(resultSet.getInt("lot_id"));
                    parkingSpot.setType(type);
                    parkingSpot.setStatus(resultSet.getString("status"));
                    parkingSpot.setPrice(resultSet.getDouble("price"));
                    list.add(parkingSpot);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<ParkingSpotDTO> getSpotsByLotId(int lot_id) {
        String selectSQL = "SELECT * FROM parking_spots WHERE lot_id = ?";
        ParkingSpotDTO parkingSpot = new ParkingSpotDTO();
        List<ParkingSpotDTO> list = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
            preparedStatement.setInt(1, lot_id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    parkingSpot = new ParkingSpotDTO();
                    parkingSpot.setSpot_id(resultSet.getInt("spot_id"));
                    parkingSpot.setLot_id(lot_id);
                    parkingSpot.setType(resultSet.getString("type"));
                    parkingSpot.setStatus(resultSet.getString("status"));
                    parkingSpot.setPrice(resultSet.getDouble("price"));
                    list.add(parkingSpot);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public ParkingSpotDTO getSpotsById(int spot_id) {
        String selectSQL = "SELECT * FROM parking_spots WHERE spot_id = ?";
        ParkingSpotDTO parkingSpot = new ParkingSpotDTO();
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
            preparedStatement.setInt(1, spot_id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    parkingSpot.setSpot_id(resultSet.getInt("spot_id"));
                    parkingSpot.setLot_id(resultSet.getInt("lot_id"));
                    parkingSpot.setType(resultSet.getString("type"));
                    parkingSpot.setStatus(resultSet.getString("status"));
                    parkingSpot.setPrice(resultSet.getDouble("price"));
                }else {
                    System.out.println("No spot found with ID: " + spot_id);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return parkingSpot;
    }
}
