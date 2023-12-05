package com.example.individualprojectbe.controller;

import com.example.individualprojectbe.domain.Airport;
import com.example.individualprojectbe.domain.AirportDto;
import com.example.individualprojectbe.exception.AirportNotFoundException;
import com.example.individualprojectbe.mapper.AirportMapper;
import com.example.individualprojectbe.service.AirportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/v1/airports")
@RequiredArgsConstructor
public class AirportController {
    private AirportService airportService;
    private AirportMapper airportMapper;
    @GetMapping
    public ResponseEntity<List<AirportDto>> getAllAirports(){
        List<Airport> airports = airportService.getAllAirports();
        return ResponseEntity.ok(airportMapper.mapToAirportDtoList(airports));
    }

    @GetMapping("{airportId}")
    public ResponseEntity<AirportDto> getAirport(@PathVariable long airportId) throws AirportNotFoundException {
        return ResponseEntity.ok(airportMapper.mapToAirportDto(airportService.getAirport(airportId)));
    }

    @DeleteMapping("{airportId}")
    public ResponseEntity<Void> deleteAirport(@PathVariable long airportId){
        airportService.deleteAirport(airportId);
        return ResponseEntity.ok().build();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AirportDto> createAirport(@RequestBody AirportDto airportDto){
        Airport airport = airportMapper.mapToAirport(airportDto);
        airportService.saveAirport(airport);
        return ResponseEntity.ok().build();
    }

    @PutMapping("{airportId}")
    public ResponseEntity<AirportDto> editAirport(@RequestBody AirportDto airportDto){
        Airport airport = airportMapper.mapToAirport(airportDto);
        Airport savedAirport = airportService.saveAirport(airport);
        return ResponseEntity.ok(airportMapper.mapToAirportDto(savedAirport));
    }
}
