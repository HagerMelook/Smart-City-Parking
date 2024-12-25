package com.example.parking.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.parking.entities.DBConnection;

public class TopUsersDAO implements DBConnection {
    public boolean checkDriver(int driver_id) {
        String selectSQL = "SELECT is_top FROM top_users WHERE driver_id = ?";
        boolean isTop= false;
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
            preparedStatement.setInt(1, driver_id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    isTop = resultSet.getBoolean("is_top");
                    System.out.println("Is top user: " + isTop);
                } else {
                    System.out.println("Driver not found.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isTop;
    }

    public void getTopDriver() {
        String selectSQL = "SELECT driver_id FROM top_users WHERE is_top = TRUE";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    System.out.println("Driver ID: " +resultSet.getInt("driver_id"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
