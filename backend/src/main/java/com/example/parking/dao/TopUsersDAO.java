package com.example.parking.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.example.parking.dto.TopUsersDTO;
import com.example.parking.entities.DBConnection;
import org.springframework.stereotype.Repository;

@Repository
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

    public List<TopUsersDTO> getTopDriver() {
        String selectSQL = "SELECT * FROM top_users WHERE is_top = TRUE ORDER BY number_of_resvs DESC";
        List<TopUsersDTO> list = new ArrayList<>();
        TopUsersDTO topUsers = new TopUsersDTO();
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(selectSQL, Statement.RETURN_GENERATED_KEYS)) {

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    topUsers = new TopUsersDTO();
                    topUsers.setDriver_id(resultSet.getInt("driver_id"));
                    topUsers.setNumber_of_resvs(resultSet.getInt("number_of_resvs"));
                    list.add(topUsers);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
