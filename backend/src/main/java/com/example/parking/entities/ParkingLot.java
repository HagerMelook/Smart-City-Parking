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
                    capacity INT DEFAULT 0,
                    regular_cap INT DEFAULT 0,
                    disabled_cap INT DEFAULT 0,
                    ev_charging_cap INT DEFAULT 0
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

    public void createTriggers() {
String insertSpotsType = """
                CREATE TRIGGER insert_spot
                AFTER INSERT ON parking_lots
                FOR EACH ROW
                BEGIN
                DECLARE counter INT DEFAULT 1;
                WHILE counter <= NEW.regular_cap DO
                    INSERT INTO parking_spots (lot_id, type) VALUES (NEW.lot_id, 'regular');
                    SET counter = counter + 1;
                END WHILE;

                SET counter = 1;
                WHILE counter <= NEW.disabled_cap DO
                    INSERT INTO parking_spots (lot_id, type) VALUES (NEW.lot_id, 'disabled');
                    SET counter = counter + 1;
                END WHILE;

                SET counter = 1;
                WHILE counter <= NEW.ev_charging_cap DO
                    INSERT INTO parking_spots (lot_id, type) VALUES (NEW.lot_id, 'ev_charging');
                    SET counter = counter + 1;
                END WHILE;
                END;
                """;
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                Statement statement = connection.createStatement()) {
            statement.executeUpdate(insertSpotsType);
            System.out.println("Triggers created successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createProcedure() {
        String insertSpotsType = """
                CREATE PROCEDURE insert_spot_type (IN  number_of_slots INT, IN type VARCHAR(50), IN lot_id INT)
                BEGIN
                    DECLARE counter INT DEFAULT 1;
                    WHILE counter <= number_of_slots DO
                        INSERT INTO parking_spots (lot_id, type)
                        VALUES (lot_id, type);
                        SET counter = counter + 1;
                    END WHILE;
                END;
                """;

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                Statement statement = connection.createStatement()) {
            statement.executeUpdate(insertSpotsType);
            System.out.println("Procedure created successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
