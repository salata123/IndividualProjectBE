package com.example.individualprojectbe.amadeus.response;

import com.example.individualprojectbe.amadeus.request.Segment;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Data
@Component
public class FlightResponse {
    private Price price;
    private int numberOfBookableSeats;
    private List<Segment> segments;

    @Data
    public static class Price {
        private String currency;
        private String total;
        private String base;
    }

    public FlightResponse() {
        this.segments = new ArrayList<>();
    }

    public static List<FlightResponse> extractFlightResponse(JsonNode rootNode) {
        List<FlightResponse> flightResponseList = new ArrayList<>();
        JsonNode dataNode = rootNode.path("data");

        for (JsonNode flightNode : dataNode) {
            FlightResponse flightResponse = new FlightResponse();
            Price price = new Price();
            price.setCurrency(flightNode.path("price").path("currency").asText());
            price.setTotal(flightNode.path("price").path("total").asText());
            price.setBase(flightNode.path("price").path("base").asText());
            flightResponse.setPrice(price);

            flightResponse.setNumberOfBookableSeats(flightNode.path("numberOfBookableSeats").asInt());

            List<Segment> segments = new ArrayList<>();
            JsonNode itinerariesNode = flightNode.path("itineraries");
            for (JsonNode itineraryNode : itinerariesNode) {
                JsonNode segmentsNode = itineraryNode.path("segments");
                for (JsonNode segmentNode : segmentsNode) {
                    Segment segment = new Segment();
                    segment.setDeparture(segmentNode.path("departure").path("iataCode").asText());
                    segment.setArrival(segmentNode.path("arrival").path("iataCode").asText());
                    segments.add(segment);
                }
            }
            flightResponse.setSegments(segments);

            flightResponseList.add(flightResponse);
        }

        return flightResponseList;
    }
}