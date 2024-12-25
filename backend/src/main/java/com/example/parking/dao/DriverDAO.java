package com.example.parking.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.parking.entities.DBConnection;

public class DriverDAO implements DBConnection {
    public void insertDriver(String name, String licensePlate, String paymentMethod) {
        String insertSQL = "INSERT INTO driver (name, license_plate, payment_method) VALUES (?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, licensePlate);
            preparedStatement.setString(3, paymentMethod);

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Insert completed. Rows affected: " + rowsAffected);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateDriverName(int driverId, String name) {
        String updateSQL = "UPDATE driver SET name = ? WHERE driver_id = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {

            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, driverId);

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Update completed. Rows affected: " + rowsAffected);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateDriverPayMeth(int driverId, String paymentMethod) {
        String updateSQL = "UPDATE driver SET payment_method = ? WHERE driver_id = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {

            preparedStatement.setString(1, paymentMethod);
            preparedStatement.setInt(2, driverId);

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Update completed. Rows affected: " + rowsAffected);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean checkLicEXIST(String licensePlate) {
        String selectSQL = "SELECT * FROM driver WHERE licence_plate= ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {

            preparedStatement.setString(1, licensePlate);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void selectDriverById(int driverId) {
        String selectSQL = "SELECT * FROM driver WHERE driver_id = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {

            preparedStatement.setInt(1, driverId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    System.out.println("Driver ID: " + resultSet.getInt("driver_id"));
                    System.out.println("Name: " + resultSet.getString("name"));
                    System.out.println("License Plate: " + resultSet.getString("license_plate"));
                    System.out.println("Payment Method: " + resultSet.getString("payment_method"));
                } else {
                    System.out.println("No driver found with ID: " + driverId);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
