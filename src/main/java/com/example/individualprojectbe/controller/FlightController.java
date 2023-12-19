package com.example.individualprojectbe.controller;

import com.example.individualprojectbe.amadeus.request.AmadeusApiRequest;
import com.example.individualprojectbe.amadeus.request.RequestData;
import com.example.individualprojectbe.amadeus.response.Flight;
import com.example.individualprojectbe.amadeus.response.FlightDto;
import com.example.individualprojectbe.domain.CartDto;
import com.example.individualprojectbe.exception.CartNotFoundException;
import com.example.individualprojectbe.exception.FlightNotFoundException;
import com.example.individualprojectbe.mapper.FlightMapper;
import com.example.individualprojectbe.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping
    public ResponseEntity<FlightDto> createFlight(@RequestBody RequestData requestData){
        for(Flight flight : amadeusApiRequest.sendFlightOffersRequest(requestData)) {
            flightService.saveFlight(flight);
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<FlightDto>> getAllFlights(){
        List<Flight> flights = flightService.getAllFlights();
        return ResponseEntity.ok(flightMapper.mapToFlightDtoList(flights));
    }

    @GetMapping("{flightId}")
    public ResponseEntity<FlightDto> getFlight(@PathVariable long flightId) throws FlightNotFoundException {
        return ResponseEntity.ok(flightMapper.mapToFlightDto(flightService.getFlight(flightId)));
    }
}
