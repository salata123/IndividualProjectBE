package com.example.individualprojectbe.amadeus.mapper;

import com.example.individualprojectbe.amadeus.response.Flight;
import com.example.individualprojectbe.amadeus.response.Price;
import com.example.individualprojectbe.amadeus.response.Segment;
import com.example.individualprojectbe.amadeus.response.Location;
import com.example.individualprojectbe.amadeus.factory.FlightBuilder;
import com.example.individualprojectbe.amadeus.factory.FlightBuilderImpl;
import com.google.gson.*;

import java.util.ArrayList;
import java.util.List;

public class JsonToFlightMapper {
    private List<Flight> flightList;

    public List<Flight> mapResponseToObject(String jsonResponse) {
        JsonElement rootElement = JsonParser.parseString(jsonResponse);
        JsonObject rootObject = rootElement.getAsJsonObject();
        JsonArray dataArray = rootObject.getAsJsonArray("data");

        Gson gson = new Gson();
        flightList = new ArrayList<>();

        for (JsonElement flightElement : dataArray) {
            FlightBuilder flightBuilder = new FlightBuilderImpl();

            flightBuilder.setPrice(gson.fromJson(flightElement.getAsJsonObject().get("price"), Price.class))
                    .setNumberOfBookableSeats(flightElement.getAsJsonObject().get("numberOfBookableSeats").getAsInt());

            JsonArray segmentsArray = flightElement
                    .getAsJsonObject()
                    .getAsJsonArray("itineraries")
                    .get(0)
                    .getAsJsonObject()
                    .getAsJsonArray("segments");

            List<Segment> segments = new ArrayList<>();

            for (JsonElement segmentElement : segmentsArray) {
                JsonObject segmentObject = segmentElement.getAsJsonObject();
                Location departure = gson.fromJson(segmentObject.getAsJsonObject("departure"), Location.class);
                Location arrival = gson.fromJson(segmentObject.getAsJsonObject("arrival"), Location.class);

                Segment segment = new Segment();
                segment.setDeparture(departure);
                segment.setArrival(arrival);

                segments.add(segment);
            }

            for (Segment segment : segments) {
                flightBuilder.addSegment(segment);
            }

            flightList.add(flightBuilder.build());
        }

        return flightList;
    }
}
