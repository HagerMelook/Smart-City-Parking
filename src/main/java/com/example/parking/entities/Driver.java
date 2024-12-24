package com.example.parking.entities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Driver implements DBConnection{

    public void createTable() {
        String createTableSQL = """
                CREATE TABLE driver (
                    driver_id INT AUTO_INCREMENT PRIMARY KEY,
                    name VARCHAR(80) NOT NULL,
                    license_plate VARCHAR(20) UNIQUE NOT NULL,
                    payment_method VARCHAR(50) NOT NULL
                )
                """;

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            Statement statement = connection.createStatement()) {

            statement.execute(createTableSQL);
            System.out.println("Table 'driver' created successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
