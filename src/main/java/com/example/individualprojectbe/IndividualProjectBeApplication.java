package com.example.individualprojectbe;

import com.example.individualprojectbe.amadeus.generators.AmadeusAccessKeyGenerator;
import com.example.individualprojectbe.amadeus.request.AmadeusApiRequest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class IndividualProjectBeApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(IndividualProjectBeApplication.class, args);

        // Retrieve the AmadeusAccessKeyGenerator bean from the application context
        AmadeusAccessKeyGenerator keyGenerator = context.getBean(AmadeusAccessKeyGenerator.class);

        // Call the generateAccessToken method
        keyGenerator.generateAccessToken();
        AmadeusApiRequest amadeusApiRequest = new AmadeusApiRequest(keyGenerator);
        amadeusApiRequest.sendFlightOffersRequest();
    }
}
