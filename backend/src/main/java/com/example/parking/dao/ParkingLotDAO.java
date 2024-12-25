package com.example.parking.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.parking.entities.DBConnection;

public class ParkingLotDAO implements DBConnection{
    public void insertParkingLot(String name, String location, int capacity) {
        String insertSQL = "INSERT INTO parking_lots (name, location, capacity) VALUES (?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, location);
            preparedStatement.setInt(3, capacity);

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Insert completed. Rows affected: " + rowsAffected);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateLotAdminName(int lot_Id, String name) {
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
    }

        public void selectLotById(int lot_Id) {
        String selectSQL = "SELECT * FROM parking_lots WHERE lot_id= ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {

            preparedStatement.setInt(1, lot_Id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    System.out.println("Lot ID: " + resultSet.getInt("driver_id"));
                    System.out.println("Name: " + resultSet.getString("name"));
                    System.out.println("Location: " + resultSet.getString("location"));
                    System.out.println("capacity: " + resultSet.getInt("capacity"));
                } else {
                    System.out.println("No parking_lot found with ID: " + lot_Id);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
