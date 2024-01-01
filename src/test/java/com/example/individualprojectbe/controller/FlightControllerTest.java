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
import com.example.individualprojectbe.visa.Visa;
import com.example.individualprojectbe.visa.VisaNotFoundException;
import com.example.individualprojectbe.visa.VisaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class FlightControllerTest {

    @Mock
    private AmadeusApiRequest amadeusApiRequest;

    @Mock
    private FlightMapper flightMapper;

    @Mock
    private FlightService flightService;

    @Mock
    private VisaService visaService;

    @InjectMocks
    private FlightController flightController;

    private Flight flight;
    private FlightDto flightDto;
    private final String departure = "PL";
    private final String arrival = "GB";

    @BeforeEach
    void setUp() {
        flight = new Flight(1L, new Price(101L, "USD", "1000", "900"), 200, new ArrayList<>(), 1L, "visa-free");
        flight.getSegments().add(new Segment(1L, new Location(301L, "JFK", "T1", "2022-01-01"),
                new Location(302L, "LAX", "T2", "2022-01-02")));

        flightDto = new FlightDto(1L, new Price(101L, "USD", "1000", "900"), 200, new ArrayList<>(), 1L, "visa-free");
        flightDto.getSegments().add(new Segment(1L, new Location(301L, "JFK", "T1", "2022-01-01"),
                new Location(302L, "LAX", "T2", "2022-01-02")));
    }

    @Test
    void createFlightTest() throws VisaNotFoundException {
        // Given
        RequestData requestData = new RequestData();

        when(amadeusApiRequest.sendFlightOffersRequest(requestData)).thenReturn(List.of(flight));
        when(visaService.checkVisaRequirements(departure, arrival, flight)).thenReturn(1L);

        Visa mockVisa = new Visa();
        mockVisa.setVisaType("visa-free");

        when(visaService.getVisa(1L)).thenReturn(mockVisa);

        // When
        ResponseEntity<FlightDto> responseEntity = flightController.createFlight(departure, arrival, requestData);

        // Then
        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCodeValue());

        verify(amadeusApiRequest, times(1)).sendFlightOffersRequest(requestData);
        verify(visaService, times(1)).checkVisaRequirements(eq(departure), eq(arrival), eq(flight));
        verify(visaService, times(1)).getVisa(1L);  // Verify that getVisa was called with the correct id
        verify(flightService, times(2)).saveFlight(flight);
    }

    @Test
    void getAllFlightsTest() {
        // Given
        List<Flight> flights = List.of(flight);
        when(flightService.getAllFlights()).thenReturn(flights);
        when(flightMapper.mapToFlightDtoList(flights)).thenReturn(List.of(flightDto));

        // When
        ResponseEntity<List<FlightDto>> responseEntity = flightController.getAllFlights();

        // Then
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
        // Given
        when(flightService.getFlight(1L)).thenReturn(flight);
        when(flightMapper.mapToFlightDto(flight)).thenReturn(flightDto);

        // When
        ResponseEntity<FlightDto> responseEntity = flightController.getFlight(1L);

        // Then
        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertNotNull(responseEntity.getBody());
        assertEquals(flightDto, responseEntity.getBody());

        verify(flightService, times(1)).getFlight(1L);
        verify(flightMapper, times(1)).mapToFlightDto(flight);
    }

    @Test
    void createFlightWithEmptyRequestTest() throws VisaNotFoundException {
        // Given
        RequestData requestData = new RequestData();

        when(amadeusApiRequest.sendFlightOffersRequest(requestData)).thenReturn(new ArrayList<>());

        // When
        ResponseEntity<FlightDto> responseEntity = flightController.createFlight(departure, arrival, requestData);

        // Then
        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCodeValue());

        verify(amadeusApiRequest, times(1)).sendFlightOffersRequest(requestData);
        verify(flightService, times(0)).saveFlight(any());
    }

    @Test
    void getFlightExceptionTest() throws FlightNotFoundException {
        // Given
        long nonExistentFlightId = 99999999L;

        when(flightService.getFlight(nonExistentFlightId)).thenThrow(new FlightNotFoundException());

        // When
        ResponseEntity<FlightDto> responseEntity = flightController.getFlight(nonExistentFlightId);

        // Then
        assertNotNull(responseEntity);
        assertEquals(404, responseEntity.getStatusCodeValue());
        assertNull(responseEntity.getBody());

        verify(flightService, times(1)).getFlight(nonExistentFlightId);
        verify(flightMapper, times(0)).mapToFlightDto(any());
    }
}
