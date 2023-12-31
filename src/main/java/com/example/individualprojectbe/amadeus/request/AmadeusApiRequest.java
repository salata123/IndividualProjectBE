package com.example.individualprojectbe.amadeus.request;

import com.example.individualprojectbe.amadeus.generators.AmadeusAccessKeyGenerator;
import com.example.individualprojectbe.amadeus.mapper.JsonToFlightMapper;
import com.example.individualprojectbe.amadeus.response.Flight;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class AmadeusApiRequest {
    private final String apiUrl = "https://test.api.amadeus.com/v2/shopping/flight-offers";
    private final AmadeusAccessKeyGenerator accessKeyGenerator;

    public AmadeusApiRequest(AmadeusAccessKeyGenerator accessKeyGenerator) {
        this.accessKeyGenerator = accessKeyGenerator;
    }

    public List<Flight> sendFlightOffersRequest(RequestData requestData) {
        String accessToken = accessKeyGenerator.generateAccessToken();
        List<Flight> flightList = new ArrayList<>();

        try {
            HttpClient httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(apiUrl);

            httpPost.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);

            httpPost.setHeader("Content-Type", "application/json");

            String jsonBody = "{\"currencyCode\":\"" + requestData.getCurrencyCode() + "\",\"originDestinations\":[{\"id\":\"1\",\"originLocationCode\":\"" + requestData.getOriginLocationCode() + "\",\"destinationLocationCode\":\"" + requestData.getDestinationLocationCode() + "\",\"departureDateTimeRange\":{\"date\":\"" + requestData.getDepartureDate() + "\",\"time\":\"" + requestData.getDepartureTime() + "\"}}],\"travelers\":[{\"id\":\"1\",\"travelerType\":\"ADULT\"}],\"sources\":[\"GDS\"],\"searchCriteria\":{\"maxFlightOffers\":10,\"flightFilters\":{\"cabinRestrictions\":[{\"cabin\":\"BUSINESS\",\"coverage\":\"MOST_SEGMENTS\",\"originDestinationIds\":[\"1\"]}]}}}";

            httpPost.setEntity(EntityBuilder.create().setText(jsonBody).build());

            HttpResponse response = httpClient.execute(httpPost);

            String jsonResponse = new String(response.getEntity().getContent().readAllBytes());

            System.out.println("Sending full Json response to create flight objects: " + jsonResponse); // DEBUG

            JsonToFlightMapper jsonToFlightMapper = new JsonToFlightMapper();
            flightList = jsonToFlightMapper.mapResponseToObject(jsonResponse);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flightList;
    }
}