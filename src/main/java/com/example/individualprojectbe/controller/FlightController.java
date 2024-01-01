package com.example.individualprojectbe.controller;

import com.example.individualprojectbe.amadeus.request.AmadeusApiRequest;
import com.example.individualprojectbe.amadeus.request.RequestData;
import com.example.individualprojectbe.amadeus.response.Flight;
import com.example.individualprojectbe.amadeus.response.FlightDto;
import com.example.individualprojectbe.exception.FlightNotFoundException;
import com.example.individualprojectbe.mapper.FlightMapper;
import com.example.individualprojectbe.service.FlightService;
import com.example.individualprojectbe.visa.VisaNotFoundException;
import com.example.individualprojectbe.visa.VisaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/v1/flights")
@RequiredArgsConstructor
public class FlightController {
    private final AmadeusApiRequest amadeusApiRequest;
    private final FlightMapper flightMapper;
    private final FlightService flightService;
    private final VisaService visaService;

    @PostMapping("/create/{departure}/{arrival}")
    public ResponseEntity<FlightDto> createFlight(
            @PathVariable String departure,
            @PathVariable String arrival,
            @RequestBody RequestData requestData
    ) throws VisaNotFoundException {
        for(Flight flight : amadeusApiRequest.sendFlightOffersRequest(requestData)) {
            flightService.saveFlight(flight);
            flight.setVisaId(visaService.checkVisaRequirements(departure, arrival, flight));
            flight.setVisaType(visaService.getVisa(flight.getVisaId()).getVisaType());
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
    public ResponseEntity<FlightDto> getFlight(@PathVariable long flightId) {
        try {
            FlightDto flightDto = flightMapper.mapToFlightDto(flightService.getFlight(flightId));
            return ResponseEntity.ok(flightDto);
        } catch (FlightNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
