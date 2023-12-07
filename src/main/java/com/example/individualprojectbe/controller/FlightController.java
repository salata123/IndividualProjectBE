package com.example.individualprojectbe.controller;

import com.example.individualprojectbe.amadeus.mapper.JsonToFlightMapper;
import com.example.individualprojectbe.amadeus.request.AmadeusApiRequest;
import com.example.individualprojectbe.amadeus.response.Flight;
import com.example.individualprojectbe.amadeus.response.FlightDto;
import com.example.individualprojectbe.domain.Airport;
import com.example.individualprojectbe.domain.AirportDto;
import com.example.individualprojectbe.domain.Cart;
import com.example.individualprojectbe.domain.CartDto;
import com.example.individualprojectbe.mapper.FlightMapper;
import com.example.individualprojectbe.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/v1/flights")
@RequiredArgsConstructor
public class FlightController {
    @Autowired
    private AmadeusApiRequest amadeusApiRequest;

    @Autowired
    private FlightMapper flightMapper;

    @Autowired
    private FlightService flightService;

    @PostMapping(value = "test")
    public ResponseEntity<FlightDto> createFlight(){
        for(Flight flight : amadeusApiRequest.sendFlightOffersRequest()) {
            flightService.saveFlight(flight);
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<FlightDto>> getAllFlights(){
        List<Flight> flights = flightService.getAllFlights();
        return ResponseEntity.ok(flightMapper.mapToFlightDtoList(flights));
    }
}
