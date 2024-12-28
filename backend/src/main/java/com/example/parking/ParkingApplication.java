package com.example.parking;

import com.example.parking.entities.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ParkingApplication {

	public static void main(String[] args) {
		SpringApplication.run(ParkingApplication.class, args);
		User u = new User();
		u.createTable();
		Driver driver = new Driver();
		driver.createTable();
		SysAdmin sysa = new SysAdmin();
		sysa.createTable();
		ParkingLot pl = new ParkingLot();
		pl.createTable();
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
	}

}
