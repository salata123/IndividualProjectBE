package com.example.individualprojectbe.visa;

import com.example.individualprojectbe.amadeus.response.Flight;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;
//API based on https://github.com/nickypangers/passport-visa-api
@Service
public class VisaService {
    @Autowired
    private VisaRepository repository;

    public Long checkVisaRequirements(String departureLocation, String arrivalLocation, Flight flight) {
        String API_URL = "https://rough-sun-2523.fly.dev/api/";
        String apiUrl = API_URL + departureLocation + "/" + arrivalLocation;

        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(apiUrl, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode rootNode = objectMapper.readTree(response);
            String category = rootNode.path("category").asText();

            Visa visa = new Visa();
            visa.setFlightId(flight.getId());
            visa.setVisaType(category);
            repository.save(visa);
            return visa.getId();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Visa> getAllVisas() {
        return repository.findAll();
    }

    public Visa saveVisa(final Visa visa) {
        return repository.save(visa);
    }

    public Visa getVisa(final Long id) throws VisaNotFoundException {
        return repository.findById(id).orElseThrow(VisaNotFoundException::new);
    }

    public void deleteVisa(final Long id) {
        repository.deleteById(id);
    }
}
