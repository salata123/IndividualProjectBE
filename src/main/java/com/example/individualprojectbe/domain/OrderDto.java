package com.example.individualprojectbe.domain;

import com.example.individualprojectbe.amadeus.response.Flight;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private Long id;
    private Cart cart;
    private List<Long> flights;
}
