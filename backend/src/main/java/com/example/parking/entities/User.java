package com.example.parking.entities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class User implements DBConnection {
    public void createTable() {
        String createTableSQL = """
                CREATE TABLE user (
                    user_id INT AUTO_INCREMENT PRIMARY KEY,
                    type VARCHAR(80) NOT NULL,
                    email VARCHAR(100) NOT NULL,
                    password VARCHAR(30) NOT NULL
                )
                """;

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                Statement statement = connection.createStatement()) {

            statement.execute(createTableSQL);
            System.out.println("Table 'user' created successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
