package com.example.parking.controllers;

import com.example.parking.dto.DriverDTO;
import com.example.parking.dto.LotAdminDTO;
import com.example.parking.dto.UserDTO;
import com.example.parking.services.SignUpService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/signup")
public class SignUpController {
    SignUpService signUpService;

    public SignUpController(SignUpService signUpService) {
        this.signUpService = signUpService;
    }

    @PostMapping("/driver")
    public ResponseEntity<?> driverSignUp (@RequestBody DriverDTO driver) {
        try {
            UserDTO user = signUpService.insertDriver(driver);
            if (user.getUserId() > 0) {
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to sign up driver.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }

    @PostMapping("/lot-admin")
    public ResponseEntity<?> lotAdminSignUp(@RequestBody LotAdminDTO lotAdmin){
        try {
            UserDTO user = signUpService.insertLotAdmin(lotAdmin);
            if (user.getUserId() > 0) {
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to sign up driver.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }
}
