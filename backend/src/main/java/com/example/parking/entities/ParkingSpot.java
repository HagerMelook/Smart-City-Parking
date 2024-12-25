package com.example.parking.entities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ParkingSpot implements DBConnection {
    public void createTable() {
        // price is initial price, it will be modified by the lot admin or model(dynamic
        // pricing)
        String createTableSQL = """
                CREATE TABLE parking_spots (
                    spot_id INT AUTO_INCREMENT PRIMARY KEY,
                    lot_id INT NOT NULL,
                    status ENUM('available', 'occupied', 'reserved') DEFAULT 'available',
                    type ENUM('regular', 'disabled', 'ev_charging'),
                    price DECIMAL(10, 2),
                    FOREIGN KEY (lot_id) REFERENCES parking_lots(lot_id)
                )
                """;

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                Statement statement = connection.createStatement()) {

            statement.execute(createTableSQL);
            System.out.println("Table 'parking spots' created successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createTriggers() {
        String insertTrigger = """
                CREATE TRIGGER set_price_on_insert
                BEFORE INSERT ON parking_spots
                FOR EACH ROW
                BEGIN
                    IF NEW.type = 'regular' THEN
                        SET NEW.price = 100;
                    ELSEIF NEW.type = 'ev_charging' THEN
                        SET NEW.price = 200;
                    ELSEIF NEW.type = 'disabled' THEN
                        SET NEW.price = 0;
                    END IF;
                END;
                """;

        String updateTrigger = """
                CREATE TRIGGER set_price_on_update
                BEFORE UPDATE ON parking_spots
                FOR EACH ROW
                BEGIN
                IF NEW.type = 'regular' THEN
                        SET NEW.price = 100;
                    ELSEIF NEW.type = 'ev_charging' THEN
                        SET NEW.price = 200;
                    ELSEIF NEW.type = 'disabled' THEN
                        SET NEW.price = 0;
                    END IF;
                END;
                """;

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                Statement statement = connection.createStatement()) {

            statement.executeUpdate(insertTrigger);
            statement.executeUpdate(updateTrigger);

            System.out.println("Triggers created successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
