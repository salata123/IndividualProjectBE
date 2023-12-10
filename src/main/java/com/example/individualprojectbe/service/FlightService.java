package com.example.individualprojectbe.service;

import com.example.individualprojectbe.amadeus.response.Flight;
import com.example.individualprojectbe.exception.FlightNotFoundException;
import com.example.individualprojectbe.repository.FlightRepository;
import com.example.individualprojectbe.repository.PriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FlightService {
    @Autowired
    private PriceRepository priceRepository;

    @Autowired
    private FlightRepository repository;

    public List<Flight> getAllFlights() {
        return repository.findAll();
    }
    @Transactional
    public Flight saveFlight(final Flight flight) {
        priceRepository.save(flight.getPrice());
        return repository.save(flight);
    }

    public Flight getFlight(final Long id) throws FlightNotFoundException {
        return repository.findById(id).orElseThrow(FlightNotFoundException::new);
    }

    public void deleteFlight(final Long id) {
        repository.deleteById(id);
    }
}
