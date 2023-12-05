package com.example.individualprojectbe.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class AdminConfig {
    @Value("${amadeus.api.key}")
    private String amadeusApiKey;

    @Value("${amadeus.api.secret}")
    private String amadeusApiSecret;
}