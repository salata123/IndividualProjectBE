package com.example.individualprojectbe.controller;

import com.example.individualprojectbe.domain.Ticket;
import com.example.individualprojectbe.domain.TicketDto;
import com.example.individualprojectbe.exception.TicketNotFoundException;
import com.example.individualprojectbe.mapper.TicketMapper;
import com.example.individualprojectbe.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/v1/tickets")
@RequiredArgsConstructor
public class TicketController {
    private final TicketService ticketService;
    private final TicketMapper ticketMapper;

    @GetMapping
    public ResponseEntity<List<TicketDto>> getTickets(){
        List<Ticket> tickets = ticketService.getAllTickets();
        return ResponseEntity.ok(ticketMapper.mapToTicketDtoList(tickets));
    }

    @GetMapping(value = "{ticketId}")
    public ResponseEntity<TicketDto> getTicket(@PathVariable Long ticketId) throws TicketNotFoundException {
        return ResponseEntity.ok(ticketMapper.mapToTicketDto(ticketService.getTicket(ticketId)));
    }

    @DeleteMapping(value = "{ticketId}")
    public ResponseEntity<TicketDto> deleteTicket(@PathVariable Long ticketId){
        ticketService.deleteTicket(ticketId);
        return ResponseEntity.ok().build();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createTicket(@RequestBody TicketDto ticketDto){
        Ticket ticket = ticketMapper.mapToTicket(ticketDto);
        ticketService.saveTicket(ticket);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<TicketDto> editTicket(@RequestBody TicketDto ticketDto){
        Ticket ticket = ticketMapper.mapToTicket(ticketDto);
        Ticket savedTicket = ticketService.saveTicket(ticket);
        return ResponseEntity.ok(ticketMapper.mapToTicketDto(savedTicket));
    }
}
