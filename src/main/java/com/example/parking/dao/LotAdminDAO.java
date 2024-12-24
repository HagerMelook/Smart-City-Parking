package com.example.parking.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.parking.entities.DBConnection;

public class LotAdminDAO implements DBConnection {
        public void insertLotAdmin(String name, int lot_id) {
        String insertSQL = "INSERT INTO lot_admin (name, lot_id) VALUES (?, ?)";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {

            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, lot_id);

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Insert completed. Rows affected: " + rowsAffected);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateLotAdminName(int lot_admin_Id, String name) {
        String updateSQL = "UPDATE lot_admin SET name = ? WHERE lot_admin_id = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {

            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, lot_admin_Id);

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Update completed. Rows affected: " + rowsAffected);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

        public void selectLotAdminById(int lot_admin_Id) {
        String selectSQL = "SELECT * FROM lot_admin WHERE lot_admin_id= ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {

            preparedStatement.setInt(1, lot_admin_Id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    System.out.println("Lot Admin ID: " + resultSet.getInt("driver_id"));
                    System.out.println("Name: " + resultSet.getString("name"));
                    System.out.println("Lot ID: " + resultSet.getInt("lot_id"));
                } else {
                    System.out.println("No lot_admin found with ID: " + lot_admin_Id);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean checkAdminForLotEXIST(int lot_id) {
        String selectSQL = "SELECT * FROM lot_admin WHERE lot_id= ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {

            preparedStatement.setInt(1, lot_id);

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
}
