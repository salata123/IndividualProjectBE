package com.example.individualprojectbe.amadeus.response;

import lombok.Data;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "PRICES")
public class Price {
    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;

    @Column(name = "CURRENCY")
    private String currency;

    @Column(name = "TOTAL")
    private String total;

    @Column(name = "BASE")
    private String base;
}