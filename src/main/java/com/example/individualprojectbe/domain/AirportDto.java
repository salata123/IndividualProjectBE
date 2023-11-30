package com.example.individualprojectbe.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AirportDto {
    private Long id;
    private String name;
    private String country;
    private String city;
}
