package com.example.parking.entities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Reservations implements DBConnection {
    public void createTable() {
        String createTableSQL = """
                CREATE TABLE reservations (
                    resv_id INT AUTO_INCREMENT PRIMARY KEY,
                    driver_id INT NOT NULL,
                    spot_id INT NOT NULL,
                    start_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    end_time TIMESTAMP,
                    status ENUM('active','confirmed', 'expired', 'canceled') DEFAULT 'active',
                    penalty DECIMAL(10, 2) DEFAULT 0,
                    FOREIGN KEY (driver_id) REFERENCES driver(driver_id),
                    FOREIGN KEY (spot_id) REFERENCES parking_spots(spot_id)
                )
                """;

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                Statement statement = connection.createStatement()) {

            statement.execute(createTableSQL);
            System.out.println("Table 'reservations' created successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createTriggers() {
        String applyPenaltyTrigger = """
                CREATE TRIGGER apply_penalty
                BEFORE INSERT ON reservations
                FOR EACH ROW
                BEGIN
                    SET NEW.end_time = DATE_ADD(CURRENT_TIMESTAMP, INTERVAL 2 HOUR);
                    SET NEW.penalty = (SELECT price * 2 FROM parking_spots WHERE spot_id = NEW.spot_id);
                END;
                """;

        String updateSpotStatus = """
                CREATE TRIGGER update_spot
                AFTER INSERT ON reservations
                FOR EACH ROW
                BEGIN
                    UPDATE parking_spots
                    SET status = 'reserved'
                    WHERE spot_id = NEW.spot_id;
                END;
                """;

        String createEventQuery = """
                CREATE EVENT update_expired_reservations
                ON SCHEDULE EVERY 30 MINUTE
                DO
                BEGIN
                    UPDATE parking_spots
                    SET status = 'available'
                    WHERE spot_id IN (
                        SELECT spot_id
                        FROM reservations
                        WHERE (status = 'active' OR status = 'confirmed') AND CURRENT_TIMESTAMP >= end_time
                    );

                    INSERT INTO transactions (driver_id, amount)
                    SELECT r.driver_id, r.penalty
                    FROM reservations r
                    WHERE r.status = 'active' AND CURRENT_TIMESTAMP >= r.end_time;

                    UPDATE reservations
                    SET status = 'expired'
                    WHERE (status = 'active' OR status = 'confirmed') AND CURRENT_TIMESTAMP >= end_time;
                END;
                """;

        String cancelledStatusTrigger = """
                CREATE TRIGGER update_cancelled_status
                AFTER UPDATE ON reservations
                FOR EACH ROW
                BEGIN
                    IF  NEW.status = 'canceled' THEN
                        UPDATE parking_spots
                        SET status = 'available'
                        WHERE spot_id = NEW.spot_id;
                        INSERT INTO transactions (driver_id, amount)
                        VALUES (NEW.driver_id, NEW.penalty);
                    END IF;
                END;
                """;

        String confirmedStatusTrigger = """
                CREATE TRIGGER update_confirmed_status
                AFTER UPDATE ON reservations
                FOR EACH ROW
                BEGIN
                    IF NEW.status = 'confirmed' THEN
                        UPDATE parking_spots
                        SET status = 'occupied'
                        WHERE spot_id = NEW.spot_id;
                        IF NEW.driver_id IN (SELECT driver_id FROM top_users) THEN
                            UPDATE top_users
                            SET number_of_resvs = number_of_resvs + 1
                            WHERE driver_id = NEW.driver_id;
                        ELSE
                            INSERT INTO top_users (driver_id)
                            VALUES (NEW.driver_id);
                        END IF;
                    END IF;
                END;
                """;

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                Statement statement = connection.createStatement()) {

            statement.executeUpdate(applyPenaltyTrigger);
            statement.executeUpdate(updateSpotStatus);
            statement.executeUpdate(createEventQuery);
            statement.executeUpdate(cancelledStatusTrigger);
            statement.executeUpdate(confirmedStatusTrigger);

            System.out.println("Triggers created successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
