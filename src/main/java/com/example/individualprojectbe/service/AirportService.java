package com.example.individualprojectbe.service;

import com.example.individualprojectbe.controller.AirportNotFoundException;
import com.example.individualprojectbe.domain.Airport;
import com.example.individualprojectbe.repository.AirportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AirportService {
    @Autowired
    private AirportRepository repository;

    public List<Airport> getAllAirports() {
        return repository.findAll();
    }

    public Airport saveAirport(final Airport airport) {
        return repository.save(airport);
    }

    public Airport getAirport(final Long id) throws AirportNotFoundException {
        return repository.findById(id).orElseThrow(AirportNotFoundException::new);
    }

    public void deleteAirport(final Long id) {
        repository.deleteById(id);
    }
}
