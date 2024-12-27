package com.example.parking.entities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Notification implements DBConnection{
        public void createTable() {
        String createTableSQL = """
                CREATE TABLE notification (
                    notification_id INT AUTO INCREMENT PRIMARY KEY,
                    user_id INT NOT NULL,
                    type VARCHAR(50),
                    msg VARCHAR(500),
                    time TIMESTAMP DEFAULT CURRENT TIMESTAMP,
                    FOREIGN KEY (user_id) REFERENCES user(user_id)
                )
                """;

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            Statement statement = connection.createStatement()) {

            statement.execute(createTableSQL);
            System.out.println("Table 'notification' created successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
