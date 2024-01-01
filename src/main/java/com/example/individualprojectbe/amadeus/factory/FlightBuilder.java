package com.example.individualprojectbe.amadeus.factory;

import com.example.individualprojectbe.amadeus.response.Flight;
import com.example.individualprojectbe.amadeus.response.Price;
import com.example.individualprojectbe.amadeus.response.Segment;

public interface FlightBuilder {
    FlightBuilder setPrice(Price price);
    FlightBuilder setNumberOfBookableSeats(int numberOfBookableSeats);
    FlightBuilder addSegment(Segment segment);
    Flight build();
}
