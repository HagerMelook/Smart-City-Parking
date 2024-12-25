package com.example.parking.entities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ParkingLot implements DBConnection {

    public void createTable() {
        // it is not allowed to make POINT data type to be unique
        String createTableSQL = """
                CREATE TABLE parking_lots (
                    lot_id INT AUTO_INCREMENT PRIMARY KEY,
                    name VARCHAR(80) UNIQUE NOT NULL,
                    location POINT NOT NULL,
                    capacity INT DEFAULT 0
                )
                """;

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                Statement statement = connection.createStatement()) {

            statement.execute(createTableSQL);
            System.out.println("Table 'parkingLots' created successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
