package com.example.parking.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.parking.dto.ParkingLotDTO;
import com.example.parking.entities.DBConnection;

@Repository
public class ParkingLotDAO implements DBConnection {
    public int insertParkingLot(ParkingLotDTO lot) {
        String insertSQL = "INSERT INTO parking_lots (name, location, capacity, regular_cap,disabled_cap, ev_charging_cap) VALUES (?, ST_GeomFromText(?), ?,?,?,?)";
        int generatedId = 0;
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(insertSQL, PreparedStatement.RETURN_GENERATED_KEYS)) {

            String point = String.format("POINT(%f %f)", lot.getLatitude(), lot.getLongitude());
            preparedStatement.setString(1, lot.getName());
            preparedStatement.setString(2, point);
            preparedStatement.setInt(3, lot.getCapacity());
            preparedStatement.setInt(4, lot.getRegular_cap());
            preparedStatement.setInt(5, lot.getDisabled_cap());
            preparedStatement.setInt(6, lot.getEv_charging_cap());

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

    public String updateLotName(int lot_Id, String name) {
        String updateSQL = "UPDATE parking_lots SET name = ? WHERE lot_id = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {

            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, lot_Id);

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Update completed. Rows affected: " + rowsAffected);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Updated Completed Successfully";
    }

    public ParkingLotDTO getLotById(int lot_Id) {
        String selectSQL = "SELECT * FROM parking_lots WHERE lot_id= ?";
        ParkingLotDTO parkingLot = new ParkingLotDTO();
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {

            preparedStatement.setInt(1, lot_Id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    parkingLot.setLot_id(lot_Id);
                    parkingLot.setName(resultSet.getString("name"));
                    parkingLot.setCapacity(resultSet.getInt("capacity"));
                    parkingLot.setRegular_cap(resultSet.getInt("regular_cap"));
                    parkingLot.setDisabled_cap(resultSet.getInt("disabled_cap"));
                    parkingLot.setEv_charging_cap(resultSet.getInt("ev_charging_cap"));
                    parkingLot.setLocation(resultSet.getString("location"));
                } else {
                    System.out.println("No parking_lot found with ID: " + lot_Id);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return parkingLot;
    }

    public List<ParkingLotDTO> getAllLots() {
        String selectSQL = """
            SELECT 
                lot_id, 
                name, 
                ST_AsText(location) AS location, 
                capacity, 
                regular_cap, 
                disabled_cap, 
                ev_charging_cap 
            FROM parking_lots
            """;

        List<ParkingLotDTO> list = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    ParkingLotDTO parkingLot = new ParkingLotDTO();
                    parkingLot.setLot_id(resultSet.getInt("lot_id"));
                    parkingLot.setName(resultSet.getString("name"));
                    parkingLot.setCapacity(resultSet.getInt("capacity"));
                    parkingLot.setLocation(resultSet.getString("location"));
                    parkingLot.setRegular_cap(resultSet.getInt("regular_cap"));
                    parkingLot.setDisabled_cap(resultSet.getInt("disabled_cap"));
                    parkingLot.setEv_charging_cap(resultSet.getInt("ev_charging_cap"));
                    list.add(parkingLot);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

}
