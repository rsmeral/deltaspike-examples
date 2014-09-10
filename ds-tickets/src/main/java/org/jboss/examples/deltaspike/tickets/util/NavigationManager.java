package org.jboss.examples.deltaspike.tickets.util;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

import org.apache.deltaspike.core.api.config.view.ViewConfig;
import org.jboss.examples.deltaspike.tickets.model.Bus;
import org.jboss.examples.deltaspike.tickets.model.dto.BusDto;
import org.jboss.examples.deltaspike.tickets.model.dto.LineDto;
import org.jboss.examples.deltaspike.tickets.model.dto.OrderDto;
import org.jboss.examples.deltaspike.tickets.model.dto.SeatsDto;
import org.jboss.examples.deltaspike.tickets.model.dto.TicketDto;
import org.jboss.examples.deltaspike.tickets.model.mapper.BusMapper;
import org.jboss.examples.deltaspike.tickets.repositories.OrderRepository;

@Model
public class NavigationManager {

    @Inject
    private TicketDto ticketDto;

    @Inject
    private LineDto lineDto;

    @Inject
    private SeatsDto seatsDto;

    @Inject
    private OrderDto orderDto;

    @Inject
    private OrderRepository orderRepository;

    @Inject
    private BusMapper busMapper;

    public Class<? extends ViewConfig> createTicket(Bus bus) {
        ticketDto.setBusDto((BusDto) busMapper.mapResult(bus));
        return Pages.Seat.class;
    }

    public Class<? extends ViewConfig> submitBusLine() {
        if (lineDto != null && lineDto.getDeparture() != null && lineDto.getArrival() != null
            && !lineDto.getDeparture().equals(lineDto.getArrival())) {
            return Pages.Date.class;
        }
        return null;
    }

    public Class<? extends ViewConfig> submitSeats() {

        if (ticketDto.getBusDto() == null || ticketDto.getBusDto().getLineDto() == null
            || ticketDto.getBusDto().getDate() == null) {
            return Pages.BusLine.class;
        }
        if (seatsDto.getChosenSeats() == null || seatsDto.getChosenSeats().size() == 0) {
            return Pages.Seat.class;
        }

        List<TicketDto> tickets = new ArrayList<TicketDto>();
        orderDto.setToPay(addTickets(tickets, seatsDto.getChosenSeats()));
        orderDto.setTicketsDto(tickets);

        return Pages.Overview.class;
    }

    private double addTickets(List<TicketDto> tickets, List<String> seatsToAdd) {
        double price = 0;
        if (seatsToAdd != null) {

            for (String seat : seatsToAdd) {
                boolean isFirstClass = Integer.valueOf(seat.substring(0, seat.length() - 1)) < 4;
                TicketDto orderTicket = new TicketDto(ticketDto.getBusDto(), seat, isFirstClass);
                orderTicket.setSeatNumber(seat);
                orderTicket.setInFirstClass(isFirstClass);
                tickets.add(orderTicket);
                price += ticketDto.getBusDto().getLineDto().getPrice() * (isFirstClass ? 1.5 : 1.0);
            }
        }
        return price;
    }

    public Class<? extends ViewConfig> order() {
        orderDto.setId(orderRepository.save(orderDto).getId());
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
