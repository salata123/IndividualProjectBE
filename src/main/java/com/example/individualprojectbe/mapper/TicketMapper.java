package com.example.individualprojectbe.mapper;

import com.example.individualprojectbe.domain.Ticket;
import com.example.individualprojectbe.domain.TicketDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketMapper {
    public Ticket mapToTicket(final TicketDto ticketDto){
        return new Ticket(
                ticketDto.getId(),
                ticketDto.getCart(),
                ticketDto.getStartAirport(),
                ticketDto.getBetweenAirports(),
                ticketDto.getEndAirport(),
                ticketDto.getDateOfFlight()
        );
    }

    public TicketDto mapToTicketDto(final Ticket ticket){
        return new TicketDto(
                ticket.getId(),
                ticket.getCart(),
                ticket.getStartAirport(),
                ticket.getBetweenAirports(),
                ticket.getEndAirport(),
                ticket.getDateOfFlight()
        );
    }

    public List<TicketDto> mapToTicketDtoList(final List<Ticket> ticketList){
        return ticketList.stream()
                .map(this::mapToTicketDto)
                .toList();
    }
}