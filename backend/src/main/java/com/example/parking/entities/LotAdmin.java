package com.example.parking.entities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class LotAdmin implements DBConnection{
    public void createTable() {
        String createTableSQL = """
                CREATE TABLE lot_admin (
                    lot_admin_id INT PRIMARY KEY,
                    full_name VARCHAR(80) NOT NULL,
                    email VARCHAR(80) UNIQUE NOT NULL,
                    password VARCHAR(80) NOT NULL,
                    lot_id INT,
                    FOREIGN KEY (lot_id) REFERENCES parking_lots(lot_id)
                    FOREIGN KEY (lot_admin_id) REFERENCES user(user_id)
                )
                """;

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            Statement statement = connection.createStatement()) {

            statement.execute(createTableSQL);
            System.out.println("Table 'lot admin' created successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
