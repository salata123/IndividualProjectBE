package com.example.individualprojectbe.amadeus.mapper;

import com.example.individualprojectbe.amadeus.response.Flight;
import com.google.gson.*;

import java.util.ArrayList;
import java.util.List;

public class JsonToFlightMapper {
    public List<Flight> mapResponseToObject(String jsonResponse){
        JsonElement rootElement = JsonParser.parseString(jsonResponse);
        System.out.println("DEBUG ROOT ELEMENT:" + rootElement); //DEBUG
        JsonObject rootObject = rootElement.getAsJsonObject();
        System.out.println("DEBUG ROOT OBJECT:" + rootObject); //DEBUG
        JsonArray dataArray = rootObject.getAsJsonArray("data");
        System.out.println("DEBUG DATA ARRAY:" + dataArray); //DEBUG

        Gson gson = new Gson();
        List<Flight> flightList = new ArrayList<>();

        for(JsonElement flightElement : dataArray){
            Flight flight = gson.fromJson(flightElement, Flight.class);
            flightList.add(flight);

            System.out.println("Flight Element: " + flightElement.toString()); //DEBUG
            System.out.println("Flight Response: " + flight.toString()); //DEBUG

            JsonArray itinerariesArray = flightElement
                    .getAsJsonObject()
                    .getAsJsonArray("itineraries");

            if (itinerariesArray.size() > 0) {
                JsonArray segmentsArray = itinerariesArray
                        .get(0)
                        .getAsJsonObject()
                        .getAsJsonArray("segments");

                System.out.println("Segments: " + segmentsArray.toString()); //DEBUG

                if (segmentsArray.size() > 0) {
                    for (JsonElement segmentElement : segmentsArray) {
                        Flight.Segment segment = gson.fromJson(segmentElement, Flight.Segment.class);
                        System.out.println("Departure: " + segment.getDeparture()); //DEBUG
                        System.out.println("Arrival: " + segment.getArrival()); //DEBUG

                        flight.getSegments().add(segment);
                    }
                } else {
                    System.out.println("No segments found for this flight."); //CREATE EXCEPTION INSTEAD
                }
            }
        }
        System.out.println(flightList.size()); //DEBUG
        System.out.println(flightList); //DEBUG
        return flightList;
    }
}
