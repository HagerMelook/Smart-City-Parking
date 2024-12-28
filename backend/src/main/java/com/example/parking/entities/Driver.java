package com.example.parking.entities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Driver implements DBConnection{

    public void createTable() {
        String createTableSQL = """
                CREATE TABLE driver (
                    driver_id INT PRIMARY KEY,
                    full_name VARCHAR(80) NOT NULL,
                    email VARCHAR(80) UNIQUE NOT NULL,
                    password VARCHAR(80) NOT NULL,
                    license_plate VARCHAR(20) UNIQUE NOT NULL,
                    card_type VARCHAR(50) NOT NULL,
                    card_number VARCHAR(50) NOT NULL,
                    expiry_date VARCHAR(20) NOT NULL,
                    cvv INT NOT NULL,
                    FOREIGN KEY (driver_id) REFERENCES user(user_id)
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
