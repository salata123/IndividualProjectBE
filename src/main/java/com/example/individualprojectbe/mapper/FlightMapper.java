package com.example.individualprojectbe.mapper;

import com.example.individualprojectbe.amadeus.response.Flight;
import com.example.individualprojectbe.amadeus.response.FlightDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FlightMapper {
    public Flight mapToFlight(final FlightDto flightDto) {
        return new Flight(
                flightDto.getId(),
                flightDto.getPrice(),
                flightDto.getNumberOfBookableSeats(),
                flightDto.getSegments()
        );
    }

    public FlightDto mapToFlightDto(final Flight flight) {
        return new FlightDto(
                flight.getId(),
                flight.getPrice(),
                flight.getNumberOfBookableSeats(),
                flight.getSegments()
        );
    }

    public List<FlightDto> mapToFlightDtoList(final List<Flight> flightList) {
        return flightList.stream()
                .map(this::mapToFlightDto)
                .collect(Collectors.toList());
    }

    public List<Flight> mapToFlightList(final List<FlightDto> flightDtoList) {
        return flightDtoList.stream()
                .map(this::mapToFlight)
                .collect(Collectors.toList());
    }
}
