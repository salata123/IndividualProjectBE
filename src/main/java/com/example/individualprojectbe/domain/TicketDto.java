package com.example.individualprojectbe.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
public class TicketDto {
    private Long id;
    private Cart cart;
    private Airport startAirport;
    private List<Airport> betweenAirports;
    private Airport endAirport;
    private LocalDate dateOfFlight;
}