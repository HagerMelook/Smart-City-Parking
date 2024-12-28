package com.example.parking.entities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class TopUsers implements DBConnection {
    public void createTable() {
        String createTableSQL = """
                CREATE TABLE top_users (
                    driver_id INT PRIMARY KEY,
                    number_of_resvs INT DEFAULT 1,
                    is_top BOOLEAN DEFAULT FALSE,
                    FOREIGN KEY (driver_id) REFERENCES driver(driver_id)
                )
                """;

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                Statement statement = connection.createStatement()) {

            statement.execute(createTableSQL);
            System.out.println("Table 'top users' created successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createTriggers() {
        String setIsTopTrigger = """
                    CREATE TRIGGER set_is_top_user
                    BEFORE UPDATE ON top_users
                    FOR EACH ROW
                    BEGIN
                        IF NEW.number_of_resvs = 10 THEN
                            SET NEW.is_top = TRUE;
                        END IF;
                    END;
                    """;

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                Statement statement = connection.createStatement()) {
            statement.executeUpdate(setIsTopTrigger);
            System.out.println("Trigger 'set_is_top' created successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
