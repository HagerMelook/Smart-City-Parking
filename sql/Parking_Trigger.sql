-- MySQL Script generated by MySQL Workbench
-- Thu Dec 26 02:37:00 2024
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema parking
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema parking
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `parking` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `parking` ;
USE `parking`;

DELIMITER $$
USE `parking`$$
CREATE
DEFINER=`hager`@`%`
TRIGGER `parking`.`set_price_on_insert`
BEFORE INSERT ON `parking`.`parking_spots`
FOR EACH ROW
BEGIN
    IF NEW.type = 'regular' THEN
        SET NEW.price = 100;
    ELSEIF NEW.type = 'ev_charging' THEN
        SET NEW.price = 200;
    ELSEIF NEW.type = 'disabled' THEN
        SET NEW.price = 0;
    END IF;
END$$

USE `parking`$$
CREATE
DEFINER=`hager`@`%`
TRIGGER `parking`.`set_price_on_update`
BEFORE UPDATE ON `parking`.`parking_spots`
FOR EACH ROW
BEGIN
IF NEW.type = 'regular' THEN
        SET NEW.price = 100;
    ELSEIF NEW.type = 'ev_charging' THEN
        SET NEW.price = 200;
    ELSEIF NEW.type = 'disabled' THEN
        SET NEW.price = 0;
    END IF;
END$$

USE `parking`$$
CREATE
DEFINER=`hager`@`%`
TRIGGER `parking`.`apply_penalty`
BEFORE INSERT ON `parking`.`reservations`
FOR EACH ROW
BEGIN
    SET NEW.end_time = DATE_ADD(CURRENT_TIMESTAMP, INTERVAL 2 HOUR);
    SET NEW.penalty = (SELECT price * 2 FROM parking_spots WHERE spot_id = NEW.spot_id);
END$$

USE `parking`$$
CREATE
DEFINER=`hager`@`%`
TRIGGER `parking`.`update_cancelled_status`
AFTER UPDATE ON `parking`.`reservations`
FOR EACH ROW
BEGIN
    IF  NEW.status = 'canceled' THEN
        UPDATE parking_spots
        SET status = 'available'
        WHERE spot_id = NEW.spot_id;
        INSERT INTO transactions (driver_id, amount)
        VALUES (NEW.driver_id, NEW.penalty);
    END IF;
END$$

USE `parking`$$
CREATE
DEFINER=`hager`@`%`
TRIGGER `parking`.`update_confirmed_status`
AFTER UPDATE ON `parking`.`reservations`
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
END$$

USE `parking`$$
CREATE
DEFINER=`hager`@`%`
TRIGGER `parking`.`update_spot`
AFTER INSERT ON `parking`.`reservations`
FOR EACH ROW
BEGIN
    UPDATE parking_spots
    SET status = 'reserved'
    WHERE spot_id = NEW.spot_id;
END$$

USE `parking`$$
CREATE
DEFINER=`hager`@`%`
TRIGGER `parking`.`set_is_top_lot`
AFTER UPDATE ON `parking`.`top_lots`
FOR EACH ROW
BEGIN
    IF NEW.revenue >= 10000 THEN
        UPDATE top_lots
        SET is_top = TRUE
        WHERE lot_id = NEW.lot_id;
    END IF;
END$$

USE `parking`$$
CREATE
DEFINER=`hager`@`%`
TRIGGER `parking`.`set_is_top_user`
AFTER UPDATE ON `parking`.`top_users`
FOR EACH ROW
BEGIN
    IF NEW.number_of_resvs = 10 THEN
        UPDATE top_users
        SET is_top = TRUE
        WHERE driver_id = NEW.driver_id;
    END IF;
END$$


DELIMITER ;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;