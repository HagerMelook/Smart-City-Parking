package com.example.parking.entities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class TopLots implements DBConnection{
    // not used by triggers yet
    public void createTable() {
        String createTableSQL = """
                CREATE TABLE top_lots (
                    lot_id INT PRIMARY KEY,
                    revenue DECIMAL(10,2) DEFAULT 0,
                    is_top BOOLEAN DEFAULT FALSE,
                    FOREIGN KEY (lot_id) REFERENCES parking_lots(lot_id)
                )
                """;

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                Statement statement = connection.createStatement()) {

            statement.execute(createTableSQL);
            System.out.println("Table 'top lots' created successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createTriggers(){
        String setIsTopTrigger ="""
            CREATE TRIGGER set_is_top_lot
            AFTER UPDATE ON top_lots
            FOR EACH ROW
            BEGIN
                IF NEW.revenue >= 10000 THEN
                    UPDATE top_lots
                    SET is_top = TRUE
                    WHERE lot_id = NEW.lot_id;
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
