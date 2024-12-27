package com.example.parking.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import com.example.parking.dto.DriverDTO;
import com.example.parking.entities.DBConnection;

@Repository
public class DriverDAO implements DBConnection {
    public int insertDriver(DriverDTO driver) {
        String insertSQL = "INSERT INTO driver (driver_id,full_name, email, password, license_plate, card_type, card_number, expiry_date, cvv) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        int generatedId = 0;
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {

            preparedStatement.setInt(1, driver.getId());
            preparedStatement.setString(2, driver.getFull_name());
            preparedStatement.setString(3, driver.getEmail());
            preparedStatement.setString(4, driver.getPassword());
            preparedStatement.setString(5, driver.getLicense_plate());
            preparedStatement.setString(6, driver.getCard_type());
            preparedStatement.setInt(7, driver.getCard_num());
            preparedStatement.setDate(8, driver.getExpiry_date());
            preparedStatement.setInt(9, driver.getCvv());

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
        String updateSQL = "UPDATE driver SET full_name = ? WHERE driver_id = ?";

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
    

    public String updateDriverPayMeth(int driverId, String cardType) {
        String updateSQL = "UPDATE driver SET card_type = ? WHERE driver_id = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {

            preparedStatement.setString(1, cardType);
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

    public DriverDTO getDriverById(int driverId) {
        String selectSQL = "SELECT * FROM driver WHERE driver_id = ?";
        DriverDTO driver = new DriverDTO();
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {

            preparedStatement.setInt(1, driverId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    driver.setId(resultSet.getInt("driver_id"));
                    driver.setFull_name(resultSet.getString("full_name"));
                    driver.setEmail(resultSet.getString("email"));
                    driver.setPassword(resultSet.getString("password"));
                    driver.setLicense_plate(resultSet.getString("license_plate"));
                    driver.setCard_type(resultSet.getString("card_type"));
                    driver.setCard_num(resultSet.getInt("card_number"));
                    driver.setExpiry_date(resultSet.getDate("expiry_date"));
                    driver.setCvv(resultSet.getInt("cvv"));
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
