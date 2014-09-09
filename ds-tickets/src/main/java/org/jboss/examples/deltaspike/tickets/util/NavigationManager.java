package org.jboss.examples.deltaspike.tickets.util;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

import org.apache.deltaspike.core.api.config.view.ViewConfig;
import org.jboss.examples.deltaspike.tickets.model.Bus;
import org.jboss.examples.deltaspike.tickets.model.Line;
import org.jboss.examples.deltaspike.tickets.model.Order;
import org.jboss.examples.deltaspike.tickets.model.Seats;
import org.jboss.examples.deltaspike.tickets.model.Ticket;
import org.jboss.examples.deltaspike.tickets.repositories.OrderRepository;

@Model
public class NavigationManager {

    @Inject
    private Ticket ticket;

    @Inject
    private Line line;

    @Inject
    private Seats seats;

    @Inject
    private Order order;

    @Inject
    private OrderRepository orderRepository;

    public Class<? extends ViewConfig> createTicket(Bus bus) {
        ticket.setBus(bus);
        return Pages.Seat.class;
    }

    public Class<? extends ViewConfig> submitBusLine() {
        if (line != null && line.getDeparture() != null && line.getArrival() != null
            && !line.getDeparture().equals(line.getArrival())) {
            return Pages.Date.class;
        }
        return null;
    }

    public Class<? extends ViewConfig> submitSeats() {

        if (ticket.getBus() == null || ticket.getBus().getLine() == null || ticket.getBus().getDate() == null) {
            return Pages.BusLine.class;
        }
        if (seats.getChosenSeats() == null || seats.getChosenSeats().size() == 0) {
            return Pages.Seat.class;
        }

        List<Ticket> tickets = new ArrayList<Ticket>();
        order.setToPay(addTickets(tickets, seats.getChosenSeats()));
        order.setTickets(tickets);

        return Pages.Overview.class;
    }

    private double addTickets(List<Ticket> tickets, List<String> seatsToAdd) {
        double price = 0;
        if (seatsToAdd != null) {

            for (String seat : seatsToAdd) {
                boolean isFirstClass = Integer.valueOf(seat.substring(0, seat.length() - 1)) < 4;
                Ticket orderTicket = new Ticket(ticket.getBus(), seat, isFirstClass);
                orderTicket.setSeatNumber(seat);
                orderTicket.setInFirstClass(isFirstClass);
                tickets.add(orderTicket);
                price += ticket.getBus().getLine().getPrice() * (isFirstClass ? 1.5 : 1.0);
            }
        }
        return price;
    }

    public Class<? extends ViewConfig> order() {
        order.setId(orderRepository.save(order).getId());
        return Pages.Ordered.class;
    }

    public Class<? extends ViewConfig> backToBusLine() {
        return Pages.BusLine.class;
    }

    public Class<? extends ViewConfig> backToDate() {
        return Pages.Date.class;
    }

    public Class<? extends ViewConfig> backToSeat() {
        return Pages.Seat.class;
    }
}
