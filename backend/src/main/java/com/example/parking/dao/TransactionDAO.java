package com.example.parking.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.parking.dto.TransactionDTO;
import com.example.parking.entities.DBConnection;

public class TransactionDAO implements DBConnection {

    public int insertTransaction(double amount, int driver_id) {
        String insertSQL = "INSERT INTO transactions (amount, driver_id) VALUES (?, ?)";
        int generatedId = 0;
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {

            preparedStatement.setDouble(1, amount);
            preparedStatement.setInt(2, driver_id);
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Insert completed. Rows affected: " + rowsAffected);

            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        generatedId = generatedKeys.getInt("transaction_id");
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

    public String updateTransactionStatue(int transaction_id, String status) {
        String updateSQL = "UPDATE transactions SET payment_status = ? WHERE transaction_id = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {

            preparedStatement.setString(1, status);
            preparedStatement.setInt(2, transaction_id);

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Update completed. Rows affected: " + rowsAffected);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Updated Completed Successfully";
    }

    public List<TransactionDTO> getTransactionsByStatus(String status) {
        String selectSQL = "SELECT * FROM transactions WHERE payment_status= ?";
        TransactionDTO transaction = new TransactionDTO();
        List<TransactionDTO> list = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
            preparedStatement.setString(1, status);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    transaction = new TransactionDTO();
                    transaction.setTransaction_id(resultSet.getInt("transaction_id"));
                    transaction.setDriver_id(resultSet.getInt("driver_id"));
                    transaction.setPayment_status(status);
                    transaction.setTimestamp(resultSet.getTimestamp("timestamp"));
                    transaction.setAmount(resultSet.getDouble("amount"));
                    list.add(transaction);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<TransactionDTO> getTransactionsByDriverId(int driver_id) {
        String selectSQL = "SELECT * FROM transactions WHERE driver_id= ?";
        TransactionDTO transaction = new TransactionDTO();
        List<TransactionDTO> list = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
            preparedStatement.setInt(1, driver_id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    transaction = new TransactionDTO();
                    transaction.setTransaction_id(resultSet.getInt("transaction_id"));
                    transaction.setDriver_id(resultSet.getInt("driver_id"));
                    transaction.setPayment_status(resultSet.getString("payment_status"));
                    transaction.setTimestamp(resultSet.getTimestamp("timestamp"));
                    transaction.setAmount(resultSet.getDouble("amount"));
                    list.add(transaction);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public TransactionDTO getTransactionsById(int transaction_id) {
        String selectSQL = "SELECT * FROM transactions WHERE transaction_id= ?";
        TransactionDTO transaction = new TransactionDTO();
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
            preparedStatement.setInt(1, transaction_id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    transaction.setTransaction_id(resultSet.getInt("transaction_id"));
                    transaction.setDriver_id(resultSet.getInt("driver_id"));
                    transaction.setPayment_status(resultSet.getString("payment_status"));
                    transaction.setTimestamp(resultSet.getTimestamp("timestamp"));
                    transaction.setAmount(resultSet.getDouble("amount"));
                }else {
                    System.out.println("No transaction found with ID: " + transaction_id);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transaction;
    }
}
