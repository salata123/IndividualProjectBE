package com.example.individualprojectbe.mapper;

import com.example.individualprojectbe.amadeus.response.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class FlightMapperTests {
    private final FlightMapper flightMapper = Mappers.getMapper(FlightMapper.class);
    private FlightDto flightDto;
    private Flight flight;

    @BeforeEach
    void setUp() {
        Price price = new Price(1L, "USD", "100.00", "80.00");
        Segment segment1 = new Segment(1L, createLocation("JFK", "Terminal A", "10:00"), createLocation("LAX", "Terminal B", "14:00"));
        List<Segment> segments = new ArrayList<>();
        segments.add(segment1);
        flight = new Flight(1L, price, 200, segments);
        flightDto = new FlightDto(1L, price, 200, segments);
    }

    @Test
    void mapToFlight() {
        Flight mappedFlight = flightMapper.mapToFlight(flightDto);

        assertNotNull(mappedFlight);
        assertEquals(flightDto.getId(), mappedFlight.getId());
        assertEquals(flightDto.getPrice().getCurrency(), mappedFlight.getPrice().getCurrency());
        assertEquals(flightDto.getPrice().getTotal(), mappedFlight.getPrice().getTotal());
        assertEquals(flightDto.getPrice().getBase(), mappedFlight.getPrice().getBase());
        assertEquals(flightDto.getNumberOfBookableSeats(), mappedFlight.getNumberOfBookableSeats());

        assertEquals(1, mappedFlight.getSegments().size());
        assertEquals(flightDto.getSegments().get(0).getDeparture().getIataCode(), mappedFlight.getSegments().get(0).getDeparture().getIataCode());
        assertEquals(flightDto.getSegments().get(0).getArrival().getIataCode(), mappedFlight.getSegments().get(0).getArrival().getIataCode());
    }

    @Test
    void mapToFlightDto() {
        FlightDto mappedFlightDto = flightMapper.mapToFlightDto(flight);

        assertNotNull(mappedFlightDto);
        assertEquals(flight.getId(), mappedFlightDto.getId());
        assertEquals(flight.getPrice().getCurrency(), mappedFlightDto.getPrice().getCurrency());
        assertEquals(flight.getPrice().getTotal(), mappedFlightDto.getPrice().getTotal());
        assertEquals(flight.getPrice().getBase(), mappedFlightDto.getPrice().getBase());
        assertEquals(flight.getNumberOfBookableSeats(), mappedFlightDto.getNumberOfBookableSeats());

        assertEquals(flight.getSegments().get(0).getDeparture().getIataCode(), mappedFlightDto.getSegments().get(0).getDeparture().getIataCode());
        assertEquals(flight.getSegments().get(0).getArrival().getIataCode(), mappedFlightDto.getSegments().get(0).getArrival().getIataCode());
    }

    @Test
    void mapToFlightDtoList() {
        List<Flight> flightList = List.of(flight);
        List<FlightDto> mappedFlightDtoList = flightMapper.mapToFlightDtoList(flightList);

        assertNotNull(mappedFlightDtoList);
        assertEquals(1, mappedFlightDtoList.size());
        // Add more assertions if needed
    }

    @Test
    void mapToFlightList() {
        List<FlightDto> flightDtoList = List.of(flightDto);
        List<Flight> mappedFlightList = flightMapper.mapToFlightList(flightDtoList);

        assertNotNull(mappedFlightList);
        assertEquals(1, mappedFlightList.size());
    }

    private Location createLocation(String iataCode, String terminal, String at) {
        return new Location(null, iataCode, terminal, at);
    }
}
