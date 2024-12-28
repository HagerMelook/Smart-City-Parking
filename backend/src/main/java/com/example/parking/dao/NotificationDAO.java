package com.example.parking.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.parking.dto.NotificationDTO;
import com.example.parking.entities.DBConnection;

public class NotificationDAO implements DBConnection {
    public int insertDriver(NotificationDTO notification) {
        String insertSQL = "INSERT INTO notification (user_id,type, msg, time) VALUES (?, ?, ?, ?)";
        int generatedId = 0;
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {

            preparedStatement.setInt(1, notification.getUser_id());
            preparedStatement.setString(2, notification.getType());
            preparedStatement.setString(3, notification.getMsg());
            preparedStatement.setTimestamp(4, notification.getTime());

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Insert completed. Rows affected: " + rowsAffected);

            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        generatedId = generatedKeys.getInt("notification_id");
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

    public List<NotificationDTO> getNotificationByUserId(int user_id) {
        String selectSQL = "SELECT * FROM notification WHERE user_id= ?";
        NotificationDTO notification = new NotificationDTO();
        List<NotificationDTO> list = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
            preparedStatement.setInt(1, user_id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    notification = new NotificationDTO();
                    notification.setNotification_id(resultSet.getInt("notification_id"));
                    notification.setUser_id(resultSet.getInt("user_id"));
                    notification.setType(resultSet.getString("type"));
                    notification.setMsg(resultSet.getString("msg"));
                    notification.setTime(resultSet.getTimestamp("time"));
                    list.add(notification);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public NotificationDTO getNotificationById(int notification_id) {
        String selectSQL = "SELECT * FROM notification WHERE notification_id= ?";
        NotificationDTO notification = new NotificationDTO();
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
            preparedStatement.setInt(1, notification_id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    notification.setNotification_id(resultSet.getInt("notification_id"));
                    notification.setUser_id(resultSet.getInt("user_id"));
                    notification.setType(resultSet.getString("type"));
                    notification.setMsg(resultSet.getString("msg"));
                    notification.setTime(resultSet.getTimestamp("time"));
                }else {
                    System.out.println("No notification found with ID: " + notification_id);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return notification;
    }
}