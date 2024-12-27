package com.example.parking.controllers;

import com.example.parking.dto.ParkingLotDTO;
import com.example.parking.dto.ParkingSpotDTO;
import com.example.parking.services.LotsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/lots")
public class LotsController {

    LotsService lotsService;

    public LotsController(LotsService lotsService) {
        this.lotsService = lotsService;
    }

    @GetMapping("")
    List<ParkingLotDTO> retrieveAllLots (){
        return lotsService.retrieveAll();
    }

    @GetMapping("/{id}/spots")
    List<ParkingSpotDTO> retrieveLotSpots (@PathVariable int id){
        return lotsService.retrieveLotSpot(id);
    }

    @PostMapping("/append")
    ResponseEntity retrieveLotSpots (@RequestBody ParkingLotDTO lot){
        return lotsService.appendNewLot(lot);
    }

    
}
