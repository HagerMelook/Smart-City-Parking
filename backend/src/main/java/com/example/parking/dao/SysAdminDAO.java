package com.example.parking.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.parking.dto.SysAdminDTO;
import com.example.parking.entities.DBConnection;

public class SysAdminDAO implements DBConnection {
    public int insertSYSAdmin(SysAdminDTO sys_admin) {
        String insertSQL = "INSERT INTO sys_admin (sys_admin_id, full_name, email, password) VALUES (?,?,?,?)";
        int generatedId = 0;
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
            preparedStatement.setInt(1, sys_admin.getSys_admin_id());
            preparedStatement.setString(2, sys_admin.getFull_name());
            preparedStatement.setString(3, sys_admin.getEmail());
            preparedStatement.setString(4, sys_admin.getPassword());

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Insert completed. Rows affected: " + rowsAffected);

            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        generatedId = generatedKeys.getInt("sys_admin_id");
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

    public String updateSYSAdminName(int sys_admin_Id, String name) {
        String updateSQL = "UPDATE sys_admin SET full_name = ? WHERE sys_admin_id = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {

            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, sys_admin_Id);

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Update completed. Rows affected: " + rowsAffected);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Updated Completed Successfully";
    }

    public SysAdminDTO getSYSAdminById(int sys_admin_Id) {
        String selectSQL = "SELECT * FROM sys_admin WHERE sys_admin_id= ?";
        SysAdminDTO sysAdmin = new SysAdminDTO();
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {

            preparedStatement.setInt(1, sys_admin_Id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    sysAdmin.setSys_admin_id(sys_admin_Id);
                    sysAdmin.setFull_name(resultSet.getString("full_name"));
                    sysAdmin.setEmail(resultSet.getString("email"));
                    sysAdmin.setPassword(resultSet.getString("password"));
                } else {
                    System.out.println("No sys_admin found with ID: " + sys_admin_Id);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sysAdmin;
    }

}
