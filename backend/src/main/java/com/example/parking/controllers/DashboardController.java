package com.example.parking.controllers;

import com.example.parking.services.DashboardService;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/dashboard")
public class DashboardController {

    DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/lotAdmin/{lotId}")
    public String lotStatistics(@PathVariable("lotId") int lotId) {
        return dashboardService.collectStatistics(lotId);
    }

}
