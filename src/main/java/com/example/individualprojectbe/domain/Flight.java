package com.example.individualprojectbe.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "FLIGHTS")
public class Flight {
    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;

    @OneToOne
    @JoinColumn(name = "START_AIRPORT_ID")
    private Airport startAirport;

    @OneToMany
    @JoinTable(
            name = "FLIGHT_BETWEEN_AIRPORTS",
            joinColumns = @JoinColumn(name = "TICKET_ID"),
            inverseJoinColumns = @JoinColumn(name = "AIRPORT_ID"))
    private List<Airport> betweenAirports;

    @OneToOne
    @JoinColumn(name = "END_AIRPORT_ID")
    private Airport endAirport;

    @Column(name = "DATE_OF_FLIGHT")
    private LocalDate dateOfFlight;
}
