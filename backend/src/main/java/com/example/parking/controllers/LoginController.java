package com.example.parking.controllers;

import com.example.parking.dto.UserDTO;
import com.example.parking.services.LoginService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class LoginController {
    LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login (@RequestBody UserDTO user){
        try {
            System.out.println(user.toString());
            UserDTO account = loginService.checkAccount(user);
            System.out.println(account.toString());
            if (account.getUserId() > 0 && user.getPassword().equals(account.getPassword())) {
                account.setPassword(null);
                return ResponseEntity.ok(account);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("either email or password is incorrect");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }

    }
}
