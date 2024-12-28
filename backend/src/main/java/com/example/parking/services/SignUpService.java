package com.example.parking.services;

import com.example.parking.dao.DriverDAO;
import com.example.parking.dao.LotAdminDAO;
import com.example.parking.dao.ParkingLotDAO;
import com.example.parking.dao.UserDAO;
import com.example.parking.dto.DriverDTO;
import com.example.parking.dto.LotAdminDTO;
import com.example.parking.dto.ParkingLotDTO;
import com.example.parking.dto.UserDTO;
import org.springframework.stereotype.Service;

@Service
public class SignUpService {
    UserDAO userDAO;
    DriverDAO driverDAO;
    LotAdminDAO lotAdminDAO;
    ParkingLotDAO parkingLotDAO;

    public SignUpService(UserDAO userDAO, DriverDAO driverDAO, LotAdminDAO lotAdminDAO, ParkingLotDAO parkingLotDAO) {
        this.userDAO = userDAO;
        this.driverDAO = driverDAO;
        this.lotAdminDAO = lotAdminDAO;
        this.parkingLotDAO = parkingLotDAO;
    }

    public UserDTO insertDriver(DriverDTO driver){
        UserDTO account = UserDTO.builder()
                .userType("driver")
                .email(driver.getEmail())
                .password(driver.getPassword())
                .build();

        int driverId = userDAO.insertUser(account);
        driver.setId(driverId);
        driverDAO.insertDriver(driver);

        UserDTO user = UserDTO.builder()
                .userId(driverId)
                .userType("driver")
                .build();

        return user;
    }

    public UserDTO insertLotAdmin(LotAdminDTO lotAdmin){
        UserDTO account = UserDTO.builder()
                .userType("lot-admin")
                .email(lotAdmin.getEmail())
                .password(lotAdmin.getPassword())
                .build();

        ParkingLotDTO parkingLot = lotAdmin.getLotData();
        parkingLotDAO.insertParkingLot(parkingLot);

        int lotAdminId = userDAO.insertUser(account);
        lotAdmin.setLot_admin_id(lotAdminId);
        lotAdminDAO.insertLotAdmin(lotAdmin);

        UserDTO user = UserDTO.builder()
                .userId(lotAdminId)
                .userType("lot-admin")
                .build();

        return user;
    }
}
