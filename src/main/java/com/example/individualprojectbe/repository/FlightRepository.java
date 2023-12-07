package com.example.individualprojectbe.repository;

import com.example.individualprojectbe.amadeus.response.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {
}
