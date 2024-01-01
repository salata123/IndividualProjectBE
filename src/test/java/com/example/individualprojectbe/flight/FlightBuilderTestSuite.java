package com.example.individualprojectbe.flight;

import com.example.individualprojectbe.amadeus.factory.FlightBuilder;
import com.example.individualprojectbe.amadeus.factory.FlightBuilderImpl;
import com.example.individualprojectbe.amadeus.response.Flight;
import com.example.individualprojectbe.amadeus.response.Location;
import com.example.individualprojectbe.amadeus.response.Price;
import com.example.individualprojectbe.amadeus.response.Segment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class FlightBuilderTestSuite {

    private Price price;
    private Segment segment;

    @BeforeEach
    void setUp() {
        price = new Price(1L, "USD", "100.00", "50.00");
        Location departure = new Location(1L, "JFK", "Terminal 1", "Gate A");
        Location arrival = new Location(2L, "LAX", "Terminal 2", "Gate B");
        segment = new Segment(1L, departure, arrival);
    }

    @Test
    void testFlightBuilder() {
        FlightBuilder flightBuilder = new FlightBuilderImpl();

        Flight flight = flightBuilder
                .setPrice(price)
                .setNumberOfBookableSeats(150)
                .addSegment(segment)
                .build();

        assertNotNull(flight);
        assertEquals(price, flight.getPrice());
        assertEquals(150, flight.getNumberOfBookableSeats());
        assertEquals(1, flight.getSegments().size());
        assertEquals(segment, flight.getSegments().get(0));
    }

    @Test
    void testFlightConstructor() {
        Flight flight = new Flight(1L, price, 150, List.of(segment), 2L, "Business");

        assertNotNull(flight);
        assertEquals(1L, flight.getId());
        assertEquals(price, flight.getPrice());
        assertEquals(150, flight.getNumberOfBookableSeats());
        assertEquals(1, flight.getSegments().size());
        assertEquals(segment, flight.getSegments().get(0));
        assertEquals(2L, flight.getVisaId());
        assertEquals("Business", flight.getVisaType());
    }

    @Test
    void testLocationConstructor() {
        Location location = new Location(1L, "JFK", "Terminal 1", "Gate A");

        assertNotNull(location);
        assertEquals(1L, location.getId());
        assertEquals("JFK", location.getIataCode());
        assertEquals("Terminal 1", location.getTerminal());
        assertEquals("Gate A", location.getAt());
    }

    @Test
    void testPriceConstructor() {
        Price price = new Price(1L, "USD", "100.00", "50.00");

        assertNotNull(price);
        assertEquals(1L, price.getId());
        assertEquals("USD", price.getCurrency());
        assertEquals("100.00", price.getTotal());
        assertEquals("50.00", price.getBase());
    }

    @Test
    void testSegmentConstructor() {
        Location departure = new Location(1L, "JFK", "Terminal 1", "Gate A");
        Location arrival = new Location(2L, "LAX", "Terminal 2", "Gate B");
        Segment segment = new Segment(1L, departure, arrival);

        assertNotNull(segment);
        assertEquals(1L, segment.getId());
        assertEquals(departure, segment.getDeparture());
        assertEquals(arrival, segment.getArrival());
    }
}
