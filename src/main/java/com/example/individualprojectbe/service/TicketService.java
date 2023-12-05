package com.example.individualprojectbe.service;

import com.example.individualprojectbe.exception.TicketNotFoundException;
import com.example.individualprojectbe.domain.Ticket;
import com.example.individualprojectbe.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {
    @Autowired
    private TicketRepository repository;

    public List<Ticket> getAllTickets() {
        return repository.findAll();
    }

    public Ticket saveTicket(final Ticket ticket) {
        return repository.save(ticket);
    }

    public Ticket getTicket(final Long id) throws TicketNotFoundException {
        return repository.findById(id).orElseThrow(TicketNotFoundException::new);
    }

    public void deleteTicket(final Long id) {
        repository.deleteById(id);
    }
}
