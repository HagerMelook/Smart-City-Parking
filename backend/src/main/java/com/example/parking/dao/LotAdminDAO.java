package com.example.parking.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.parking.dto.LotAdminDTO;
import com.example.parking.entities.DBConnection;

public class LotAdminDAO implements DBConnection {
    public int insertLotAdmin(String name, int lot_id) {
        String insertSQL = "INSERT INTO lot_admin (name, lot_id) VALUES (?, ?)";
        int generatedId = 0;
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {

            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, lot_id);

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Insert completed. Rows affected: " + rowsAffected);

            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        generatedId = generatedKeys.getInt("lot_admin_id");
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

    public String updateLotAdminName(int lot_admin_Id, String name) {
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
        return "Updated Completed Successfully";
    }

    public LotAdminDTO getLotAdminById(int lot_admin_Id) {
        String selectSQL = "SELECT * FROM lot_admin WHERE lot_admin_id= ?";
        LotAdminDTO lotAdmin = new LotAdminDTO();
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {

            preparedStatement.setInt(1, lot_admin_Id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    lotAdmin.setLot_admin_id(lot_admin_Id);
                    lotAdmin.setName(resultSet.getString("name"));
                    lotAdmin.setLot_id(resultSet.getInt("lot_id"));
                } else {
                    System.out.println("No lot_admin found with ID: " + lot_admin_Id);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lotAdmin;
    }

    public boolean checkAdminForLotEXIST(int lot_id) {
        String selectSQL = "SELECT * FROM lot_admin WHERE lot_id= ?";
        boolean exist = false;
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {

            preparedStatement.setInt(1, lot_id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    exist =  true;
                } else {
                    exist= false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exist;
    }
}
