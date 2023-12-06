package com.example.individualprojectbe.amadeus.response;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Data
@Component
public class Flight {
    private Price price;
    private int numberOfBookableSeats;
    private List<Segment> segments;

    @Data
    public static class Price {
        private String currency;
        private String total;
        private String base;
    }

    @Data
    public static class Segment {
        private Location departure;
        private Location arrival;
        private String departureTime;
        private String arrivalTime;
    }

    @Data
    public static class Location {
        private String iataCode;
        private String terminal;
        private String at;
    }

    public Flight() {
        this.segments = new ArrayList<>();
    }
}