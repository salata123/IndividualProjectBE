package com.example.individualprojectbe.amadeus.factory;

import com.example.individualprojectbe.amadeus.response.Flight;
import com.example.individualprojectbe.amadeus.response.Price;
import com.example.individualprojectbe.amadeus.response.Segment;

public class FlightBuilderImpl implements FlightBuilder {
    private final Flight flight = new Flight();

    @Override
    public FlightBuilder setPrice(Price price) {
        flight.setPrice(price);
        return this;
    }

    @Override
    public FlightBuilder setNumberOfBookableSeats(int numberOfBookableSeats) {
        flight.setNumberOfBookableSeats(numberOfBookableSeats);
        return this;
    }

    @Override
    public FlightBuilder addSegment(Segment segment) {
        flight.getSegments().add(segment);
        return this;
    }

    @Override
    public Flight build() {
        return flight;
    }
}
