package com.example.individualprojectbe.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class CartDto {
    private Long id;
    private List<TicketDto> ticketList = new ArrayList<>();
}
