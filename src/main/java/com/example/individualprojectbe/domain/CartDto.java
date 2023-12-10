package com.example.individualprojectbe.domain;

import com.example.individualprojectbe.amadeus.response.Flight;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class CartDto {
    private Long id;
    private User user;
    private List<Long> flightList;
}
