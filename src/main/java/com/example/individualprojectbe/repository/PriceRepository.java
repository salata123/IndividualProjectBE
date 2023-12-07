package com.example.individualprojectbe.repository;

import com.example.individualprojectbe.amadeus.response.Flight;
import com.example.individualprojectbe.amadeus.response.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceRepository extends JpaRepository<Price, Long> {
}
