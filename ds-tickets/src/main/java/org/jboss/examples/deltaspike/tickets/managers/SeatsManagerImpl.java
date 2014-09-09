package org.jboss.examples.deltaspike.tickets.managers;

import java.util.List;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

import org.jboss.examples.deltaspike.tickets.model.Ticket;
import org.jboss.examples.deltaspike.tickets.repositories.TicketRepository;
import org.jboss.examples.deltaspike.tickets.util.Utils;

@Model
public class SeatsManagerImpl implements SeatsManager {

    @Inject
    private TicketRepository ticketRepository;

    @Inject
    private Ticket ticket;

    @Inject
    private Utils utils;

    public List<String> getFreeSeats() {
        if (ticket == null || ticket.getBus() == null){
            return null;
        }
        List<Ticket> busTickets = ticketRepository.getBusTickets(ticket.getBus().getId());
        List<String> freeSeats = utils.getAllBusSeats();
        for (Ticket ticket : busTickets) {
            freeSeats.remove(ticket.getSeatNumber());
        }

        return freeSeats;
    }

}
