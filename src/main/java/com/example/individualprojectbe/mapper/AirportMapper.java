package com.example.individualprojectbe.mapper;

import com.example.individualprojectbe.domain.Airport;
import com.example.individualprojectbe.domain.AirportDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AirportMapper {
    public Airport mapToAirport(final AirportDto airportDto) {
        return new Airport(
                airportDto.getId(),
                airportDto.getName(),
                airportDto.getCountry(),
                airportDto.getCity()
        );
    }

    public AirportDto mapToAirportDto(final Airport airport) {
        return new AirportDto(
                airport.getId(),
                airport.getName(),
                airport.getCountry(),
                airport.getCity()
        );
    }

    public List<AirportDto> mapToAirportDtoList(final List<Airport> airportList) {
        return airportList.stream()
                .map(this::mapToAirportDto)
                .toList();
    }

    public List<Airport> mapToAirportList(final List<AirportDto> airportList) {
        return airportList.stream()
                .map(this::mapToAirport)
                .toList();
    }
}
