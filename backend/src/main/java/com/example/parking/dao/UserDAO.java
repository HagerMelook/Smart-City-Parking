package com.example.parking.dao;

import java.sql.*;

import com.example.parking.dto.UserDTO;
import com.example.parking.entities.DBConnection;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAO implements DBConnection{
        public int insertUser(UserDTO user) {
        String insertSQL = "INSERT INTO user (type, email, password) VALUES (?, ?, ?)";
        int generatedId = 0;
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, user.getUserType());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPassword());

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

    public int getUserIdByEmail(String email) {
        String querySQL = "SELECT user_id FROM user WHERE email = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(querySQL, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, email);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("user_id");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public String getUserTypeById(int userId) {
        String querySQL = "SELECT type FROM user WHERE user_id = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(querySQL, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setInt(1, userId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("type");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getPasswordById(int userId) {
        String querySQL = "SELECT password FROM user WHERE user_id = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(querySQL)) {

            preparedStatement.setInt(1, userId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("password");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }




}
