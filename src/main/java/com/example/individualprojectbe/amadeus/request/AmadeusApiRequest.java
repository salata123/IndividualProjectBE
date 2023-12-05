package com.example.individualprojectbe.amadeus.request;

import com.example.individualprojectbe.amadeus.generators.AmadeusAccessKeyGenerator;
import com.example.individualprojectbe.amadeus.response.FlightResponse;
import com.google.gson.*;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Component
public class AmadeusApiRequest {

    private final String apiUrl = "https://test.api.amadeus.com/v2/shopping/flight-offers";
    private final AmadeusAccessKeyGenerator accessKeyGenerator;

    public AmadeusApiRequest(AmadeusAccessKeyGenerator accessKeyGenerator) {
        this.accessKeyGenerator = accessKeyGenerator;
    }
        //TO ADD: GENERATING JSONBODY WITH GIVEN PARAMETERS.
    public void sendFlightOffersRequest() {
        String accessToken = accessKeyGenerator.generateAccessToken();

        try {
            HttpClient httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(apiUrl);

            // Set the Authorization header with the access token
            httpPost.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);

            // Set the content-type header
            httpPost.setHeader("Content-Type", "application/json");

            // Set the JSON body
            String jsonBody = "{\"currencyCode\":\"EUR\",\"originDestinations\":[{\"id\":\"1\",\"originLocationCode\":\"NYC\",\"destinationLocationCode\":\"PAR\",\"departureDateTimeRange\":{\"date\":\"2023-12-07\",\"time\":\"10:00:00\"}}],\"travelers\":[{\"id\":\"1\",\"travelerType\":\"ADULT\"}],\"sources\":[\"GDS\"],\"searchCriteria\":{\"maxFlightOffers\":2,\"flightFilters\":{\"cabinRestrictions\":[{\"cabin\":\"BUSINESS\",\"coverage\":\"MOST_SEGMENTS\",\"originDestinationIds\":[\"1\"]}]}}}";
            httpPost.setEntity(EntityBuilder.create().setText(jsonBody).build());

            // Execute the request and get the response
            HttpResponse response = httpClient.execute(httpPost);

            // Parse the JSON response
            String jsonResponse = new String(response.getEntity().getContent().readAllBytes());
            JsonElement rootElement = JsonParser.parseString(jsonResponse);
            JsonObject rootObject = rootElement.getAsJsonObject();
            JsonArray dataArray = rootObject.getAsJsonArray("data");

            System.out.println(jsonResponse);

            // Extract specific information using Gson
            Gson gson = new Gson();
            List<FlightResponse> FlightResponseList = new ArrayList<>();

            for (JsonElement flightElement : dataArray) {
                FlightResponse flightResponse = gson.fromJson(flightElement, FlightResponse.class);
                FlightResponseList.add(flightResponse);
            }

            // Do something with the extracted information
            for (FlightResponse flightResponse : FlightResponseList) {
                System.out.println("Price: " + flightResponse.getPrice());
                System.out.println("Number of Bookable Seats: " + flightResponse.getNumberOfBookableSeats());

                System.out.println("Segments:");
                for (Segment segment : flightResponse.getSegments()) {
                    System.out.println("Departure: " + segment.getDeparture());
                    System.out.println("Arrival: " + segment.getArrival());
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}