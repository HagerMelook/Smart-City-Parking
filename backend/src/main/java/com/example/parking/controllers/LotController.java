package com.example.parking.controllers;

import com.example.parking.dto.ParkingLotDTO;
import com.example.parking.dto.ParkingSpotDTO;
import com.example.parking.entities.Report;
import com.example.parking.services.LotService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/lots")
public class LotController {

    LotService lotService;

    public LotController(LotService lotService) {
        this.lotService = lotService;
    }

    @GetMapping("")
    List<ParkingLotDTO> retrieveAllLots (){
        return lotService.retrieveAll();
    }

    @GetMapping("/{id}/spots")
    List<ParkingSpotDTO> retrieveLotSpots (@PathVariable int id){
        return lotService.retrieveLotSpot(id);
    }

    @PostMapping("/append")
    ResponseEntity retrieveLotSpots (@RequestBody ParkingLotDTO lot){
        return lotService.appendNewLot(lot);
    }

    @GetMapping("/{lotId}/report")
    public List<Report> lotStatistics(@PathVariable("lotId") int lotId) throws JRException, FileNotFoundException {
        return lotService.collectStatistics(lotId);
    }
}
