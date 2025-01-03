package com.example.parking.entities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SysAdmin implements DBConnection {
    public void createTable() {
        String createTableSQL = """
                CREATE TABLE sys_admin (
                    sys_admin_id INT AUTO_INCREMENT PRIMARY KEY,
                    full_name VARCHAR(80) NOT NULL,
                    email VARCHAR(80) UNIQUE NOT NULL,
                    password VARCHAR(80) NOT NULL,
                    FOREIGN KEY (sys_admin_id) REFERENCES user(user_id)
                )
                """;

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                Statement statement = connection.createStatement()) {

            statement.execute(createTableSQL);
            System.out.println("Table 'sys admin' created successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
