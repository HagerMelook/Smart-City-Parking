package com.example.parking.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.parking.entities.DBConnection;

public class SysAdminDAO implements DBConnection{
            public void insertSYSAdmin(String name) {
        String insertSQL = "INSERT INTO sys_admin (name) VALUES (?)";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {

            preparedStatement.setString(1, name);

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Insert completed. Rows affected: " + rowsAffected);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateSYSAdminName(int sys_admin_Id, String name) {
        String updateSQL = "UPDATE lot_admin SET name = ? WHERE lot_admin_id = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {

            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, sys_admin_Id);

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Update completed. Rows affected: " + rowsAffected);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

        public void selectSYSAdminById(int sys_admin_Id) {
        String selectSQL = "SELECT * FROM sys_admin WHERE sys_admin_id= ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {

            preparedStatement.setInt(1, sys_admin_Id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    System.out.println("SYS Admin ID: " + resultSet.getInt("sys_admin_id"));
                    System.out.println("Name: " + resultSet.getString("name"));
                } else {
                    System.out.println("No sys_admin found with ID: " + sys_admin_Id);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
