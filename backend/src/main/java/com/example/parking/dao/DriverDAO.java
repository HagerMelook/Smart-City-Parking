package com.example.parking.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.parking.dto.DriverDTO;
import com.example.parking.entities.DBConnection;

public class DriverDAO implements DBConnection {
    public int insertDriver(String name, String licensePlate, String paymentMethod) {
        String insertSQL = "INSERT INTO driver (name, license_plate, payment_method) VALUES (?, ?, ?)";
        int generatedId = 0;
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, licensePlate);
            preparedStatement.setString(3, paymentMethod);

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Insert completed. Rows affected: " + rowsAffected);

            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        generatedId = generatedKeys.getInt("driver_id");
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

    public String updateDriverName(int driverId, String name) {
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
        return "Updated Completed Successfully";
    }
    

    public String updateDriverPayMeth(int driverId, String paymentMethod) {
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
        return "Updated Completed Successfully";
    }

    public String updateDriverLic(int driverId, String licensePlate) {
        String updateSQL = "UPDATE driver SET license_plate = ? WHERE driver_id = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {

            preparedStatement.setString(1, licensePlate);
            preparedStatement.setInt(2, driverId);

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Update completed. Rows affected: " + rowsAffected);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Updated Completed Successfully";
    }
    public boolean checkLicEXIST(String licensePlate) {
        String selectSQL = "SELECT * FROM driver WHERE license_plate= ?";
        boolean exist = false;
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {

            preparedStatement.setString(1, licensePlate);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    exist = true;
                } else {
                    exist = false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exist;
    }

    public DriverDTO gettDriverById(int driverId) {
        String selectSQL = "SELECT * FROM driver WHERE driver_id = ?";
        DriverDTO driver = new DriverDTO();
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {

            preparedStatement.setInt(1, driverId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    driver.setId(resultSet.getInt("driver_id"));
                    driver.setName(resultSet.getString("name"));
                    driver.setLicense_plate(resultSet.getString("license_plate"));
                    driver.setPayment_method(resultSet.getString("payment_method"));
                } else {
                    System.out.println("No driver found with ID: " + driverId);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return driver;
    }
}
