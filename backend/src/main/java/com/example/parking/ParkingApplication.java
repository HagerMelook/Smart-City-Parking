package com.example.parking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.parking.entities.Driver;
import com.example.parking.entities.LotAdmin;
import com.example.parking.entities.Notification;
import com.example.parking.entities.ParkingLot;
import com.example.parking.entities.ParkingSpot;
import com.example.parking.entities.Reservations;
import com.example.parking.entities.SysAdmin;
import com.example.parking.entities.TopLots;
import com.example.parking.entities.TopUsers;
import com.example.parking.entities.Transactions;
import com.example.parking.entities.User;


@SpringBootApplication
public class ParkingApplication {
	public static void main(String[] args) {
		SpringApplication.run(ParkingApplication.class, args);
		// Simulation simulation = new Simulation();
		// simulation.SimConcurrentResv();
		// simulation.SimConcurrentOccuiped(); 
		// simulation.SimConcurrentOccuipedAndResv();
		// simulation.SimSerialUpdates();
		// simulation.SimResvThenCancelled();
		
        User user = new User();
		user.createTable();
		Driver driver = new Driver();
		driver.createTable();
		SysAdmin sysa = new SysAdmin();
		sysa.createTable();
		ParkingLot pl = new ParkingLot();
		// pl.createProcedure();
		pl.createTable();
		pl.createTriggers();
		ParkingSpot ps = new ParkingSpot();
		ps.createTable();
		ps.createTriggers();
		LotAdmin lota = new LotAdmin();
		lota.createTable();
		Reservations resv = new Reservations();
		resv.createTable();
		resv.createTriggers();
		Transactions tr = new Transactions();
		tr.createTable();
		TopLots tl = new TopLots();
		tl.createTable();
		tl.createTriggers();
		TopUsers tu = new TopUsers();
		tu.createTable();
		tu.createTriggers();
		Notification no = new Notification();
		no.createTable();

	}
}
