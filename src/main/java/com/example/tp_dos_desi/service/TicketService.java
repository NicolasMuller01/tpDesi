package com.example.tp_dos_desi.service;

import com.example.tp_dos_desi.model.Ticket;
import com.example.tp_dos_desi.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    public void guardar(Ticket ticket) {
        ticketRepository.save(ticket);
    }
}