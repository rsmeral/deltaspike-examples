package org.jboss.examples.deltaspike.tickets.managers;

import java.util.List;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

import org.jboss.examples.deltaspike.tickets.model.Ticket;
import org.jboss.examples.deltaspike.tickets.model.dto.Produced;
import org.jboss.examples.deltaspike.tickets.model.dto.TicketDto;
import org.jboss.examples.deltaspike.tickets.repositories.TicketRepository;
import org.jboss.examples.deltaspike.tickets.util.Utils;

@Model
public class SeatsManagerImpl implements SeatsManager {

    @Inject
    private TicketRepository ticketRepository;

    @Inject
    @Produced
    private TicketDto ticketDto;

    @Inject
    private Utils utils;

    public List<String> getFreeSeats() {
        if (ticketDto == null || ticketDto.getBusDto() == null) {
            return null;
        }
        List<Ticket> busTickets = ticketRepository.getBusTickets(ticketDto.getBusDto().getId());
        List<String> freeSeats = utils.getAllBusSeats();
        for (Ticket ticket : busTickets) {
            freeSeats.remove(ticket.getSeatNumber());
        }

        return freeSeats;
    }

}
