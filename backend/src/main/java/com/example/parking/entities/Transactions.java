package com.example.parking.entities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Transactions implements DBConnection {

    public void createTable() {
        String createTableSQL = """
                CREATE TABLE transactions (
                    transaction_id INT AUTO_INCREMENT PRIMARY KEY,
                    driver_id INT NOT NULL,
                    amount DECIMAL(10, 2),
                    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    payment_status ENUM('pending', 'completed') DEFAULT 'pending',
                    FOREIGN KEY (driver_id) REFERENCES driver(driver_id)
                )
                """;

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                Statement statement = connection.createStatement()) {

            statement.execute(createTableSQL);
            System.out.println("Table 'transactions' created successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
