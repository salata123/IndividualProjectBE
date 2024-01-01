package com.example.individualprojectbe.visa;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "VISAS")
public class Visa {
    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;

    @Column(name = "FLIGHT_ID")
    private Long flightId;

    @Column(name = "VISA_TYPE")
    private String visaType;
}
