package com.example.individualprojectbe.controller;

import com.example.individualprojectbe.amadeus.request.AmadeusApiRequest;
import com.example.individualprojectbe.amadeus.request.RequestData;
import com.example.individualprojectbe.amadeus.response.Flight;
import com.example.individualprojectbe.amadeus.response.FlightDto;
import com.example.individualprojectbe.amadeus.response.Location;
import com.example.individualprojectbe.amadeus.response.Price;
import com.example.individualprojectbe.amadeus.response.Segment;
import com.example.individualprojectbe.exception.FlightNotFoundException;
import com.example.individualprojectbe.mapper.FlightMapper;
import com.example.individualprojectbe.service.FlightService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FlightControllerTest {

    @Mock
    private AmadeusApiRequest amadeusApiRequest;

    @Mock
    private FlightMapper flightMapper;

    @Mock
    private FlightService flightService;

    @InjectMocks
    private FlightController flightController;

    private Flight flight;
    private FlightDto flightDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize objects for testing
        flight = new Flight(1L, new Price(101L, "USD", "1000", "900"), 200, new ArrayList<>());
        flight.getSegments().add(new Segment(1L, new Location(301L, "JFK", "T1", "2022-01-01"),
                new Location(302L, "LAX", "T2", "2022-01-02")));

        flightDto = new FlightDto(1L, new Price(101L, "USD", "1000", "900"), 200, new ArrayList<>());
        flightDto.getSegments().add(new Segment(1L, new Location(301L, "JFK", "T1", "2022-01-01"),
                new Location(302L, "LAX", "T2", "2022-01-02")));
    }

    @Test
    void createFlightTest() {
        RequestData requestData = new RequestData(); // Add appropriate data for testing

        when(amadeusApiRequest.sendFlightOffersRequest(requestData)).thenReturn(List.of(flight));

        ResponseEntity<FlightDto> responseEntity = flightController.createFlight(requestData);

        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCodeValue());

        verify(amadeusApiRequest, times(1)).sendFlightOffersRequest(requestData);
        verify(flightService, times(1)).saveFlight(flight);
    }

    @Test
    void getAllFlightsTest() {
        List<Flight> flights = List.of(flight);
        when(flightService.getAllFlights()).thenReturn(flights);
        when(flightMapper.mapToFlightDtoList(flights)).thenReturn(List.of(flightDto));

        ResponseEntity<List<FlightDto>> responseEntity = flightController.getAllFlights();

        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertNotNull(responseEntity.getBody());
        assertEquals(1, responseEntity.getBody().size());
        assertEquals(flightDto, responseEntity.getBody().get(0));

        verify(flightService, times(1)).getAllFlights();
        verify(flightMapper, times(1)).mapToFlightDtoList(flights);
    }

    @Test
    void getFlightTest() throws FlightNotFoundException {
        when(flightService.getFlight(1L)).thenReturn(flight);
        when(flightMapper.mapToFlightDto(flight)).thenReturn(flightDto);

        ResponseEntity<FlightDto> responseEntity = flightController.getFlight(1L);

        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertNotNull(responseEntity.getBody());
        assertEquals(flightDto, responseEntity.getBody());

        verify(flightService, times(1)).getFlight(1L);
        verify(flightMapper, times(1)).mapToFlightDto(flight);
    }

    @Test
    void getFlightNotFoundTest() throws FlightNotFoundException {
        when(flightService.getFlight(1L)).thenThrow(new FlightNotFoundException());

        ResponseEntity<FlightDto> responseEntity = flightController.getFlight(1L);

        assertNotNull(responseEntity);
        assertEquals(404, responseEntity.getStatusCodeValue());
        assertNull(responseEntity.getBody());

        verify(flightService, times(1)).getFlight(1L);
        verify(flightMapper, times(0)).mapToFlightDto(flight);
    }

    @Test
    void createFlightWithEmptyRequestTest() {
        RequestData requestData = new RequestData(); // Add appropriate data for testing

        when(amadeusApiRequest.sendFlightOffersRequest(requestData)).thenReturn(new ArrayList<>());

        ResponseEntity<FlightDto> responseEntity = flightController.createFlight(requestData);

        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCodeValue());

        verify(amadeusApiRequest, times(1)).sendFlightOffersRequest(requestData);
        verify(flightService, times(0)).saveFlight(any());
    }

    @Test
    void createFlightWithNullResponseTest() {
        RequestData requestData = new RequestData(); // Add appropriate data for testing

        when(amadeusApiRequest.sendFlightOffersRequest(requestData)).thenReturn(null);

        ResponseEntity<FlightDto> responseEntity = flightController.createFlight(requestData);

        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCodeValue());

        verify(amadeusApiRequest, times(1)).sendFlightOffersRequest(requestData);
        verify(flightService, times(0)).saveFlight(any());
    }

    @Test
    void createFlightWithExceptionTest() {
        RequestData requestData = new RequestData();

        when(amadeusApiRequest.sendFlightOffersRequest(requestData)).thenThrow(new RuntimeException());

        ResponseEntity<FlightDto> responseEntity = flightController.createFlight(requestData);

        assertNotNull(responseEntity);
        assertEquals(500, responseEntity.getStatusCodeValue());

        verify(amadeusApiRequest, times(1)).sendFlightOffersRequest(requestData);
        verify(flightService, times(0)).saveFlight(any());
    }
}