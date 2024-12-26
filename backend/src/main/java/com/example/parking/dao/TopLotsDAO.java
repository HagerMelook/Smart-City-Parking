package com.example.parking.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.parking.dto.TopLotsDTO;
import com.example.parking.entities.DBConnection;

public class TopLotsDAO implements DBConnection {
    public String insertTopLot(int lot_id, double revenue, boolean is_top) {
        String insertSQL = "INSERT INTO top_lots (lot_id, revenue, is_top) VALUES (?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {

            preparedStatement.setInt(1, lot_id);
            preparedStatement.setDouble(2, revenue);
            preparedStatement.setBoolean(3, is_top);

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Insert completed. Rows affected: " + rowsAffected);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Inserted Completed Successfully";
    }

    public String updateLotRev(int lotId, double amount) {
        String updateSQL = "UPDATE top_lots SET revenue = revenue+? WHERE lot_id = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {

            preparedStatement.setDouble(1, amount);
            preparedStatement.setInt(2, lotId);

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Update completed. Rows affected: " + rowsAffected);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Updated Completed Successfully";
    }

    public boolean checkLot(int lot_id) {
        String selectSQL = "SELECT is_top FROM top_lots WHERE lot_id = ?";
        boolean isTop = false;
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
            preparedStatement.setInt(1, lot_id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    isTop = resultSet.getBoolean("is_top");
                    System.out.println("Is top lot: " + isTop);
                } else {
                    System.out.println("lot not found.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isTop;
    }

    public boolean checkLotExist(int lot_id) {
        String selectSQL = "SELECT * FROM top_lots WHERE lot_id = ?";
        boolean isExist = false;
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
            preparedStatement.setInt(1, lot_id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    isExist = true;
                } else {
                    isExist = false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isExist;
    }

    public List<TopLotsDTO> getTopLots() {
        String selectSQL = "SELECT * FROM top_lots WHERE is_top = TRUE";
        List<TopLotsDTO> list = new ArrayList<>();
        TopLotsDTO topLots = new TopLotsDTO();
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    topLots.setLot_id(resultSet.getInt("lot_id"));
                    topLots.setRevenue(resultSet.getInt("revenue"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
