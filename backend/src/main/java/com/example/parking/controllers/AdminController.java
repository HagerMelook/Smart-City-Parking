package com.example.parking.controllers;

import com.example.parking.services.AdminService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;

@RestController
@CrossOrigin
@RequestMapping("/admin")
public class AdminController {

    AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/topUsers")
    public ResponseEntity<FileSystemResource> getTopUsers() throws FileNotFoundException, JRException {
        return adminService.returnTopUsers();
    }

    @GetMapping("/topLots")
    public ResponseEntity<FileSystemResource> getTopLots() throws FileNotFoundException, JRException {
        return adminService.returnTopLots();
    }

}
