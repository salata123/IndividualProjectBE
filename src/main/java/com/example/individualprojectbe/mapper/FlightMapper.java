package com.example.individualprojectbe.mapper;

import com.example.individualprojectbe.amadeus.response.Flight;
import com.example.individualprojectbe.amadeus.response.FlightDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FlightMapper {
    Flight mapToFlight(FlightDto flightDto);
    FlightDto mapToFlightDto(Flight flight);
    List<FlightDto> mapToFlightDtoList(List<Flight> flightList);
    List<Flight> mapToFlightList(List<FlightDto> flightDtoList);
}
