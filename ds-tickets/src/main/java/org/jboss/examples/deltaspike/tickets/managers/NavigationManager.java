package org.jboss.examples.deltaspike.tickets.managers;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

import org.apache.deltaspike.core.api.config.view.ViewConfig;
import org.apache.deltaspike.core.api.config.view.navigation.NavigationParameterContext;
import org.jboss.examples.deltaspike.tickets.model.Bus;
import org.jboss.examples.deltaspike.tickets.model.dto.BusDto;
import org.jboss.examples.deltaspike.tickets.model.dto.LineDto;
import org.jboss.examples.deltaspike.tickets.model.dto.OrderDto;
import org.jboss.examples.deltaspike.tickets.model.dto.Produced;
import org.jboss.examples.deltaspike.tickets.model.dto.SeatsDto;
import org.jboss.examples.deltaspike.tickets.model.dto.TicketDto;
import org.jboss.examples.deltaspike.tickets.model.mapper.BusMapper;
import org.jboss.examples.deltaspike.tickets.repositories.OrderRepository;
import org.jboss.examples.deltaspike.tickets.util.Pages;
import org.jboss.examples.deltaspike.tickets.util.Pages.Access;
import org.jboss.examples.deltaspike.tickets.util.Utils;

@Model
public class NavigationManager {

    @Inject
    @Produced
    private TicketDto ticketDto;

    @Inject
    @Produced
    private LineDto lineDto;

    @Inject
    @Produced
    private SeatsDto seatsDto;

    @Inject
    @Produced
    private OrderDto orderDto;

    @Inject
    private OrderRepository orderRepository;

    @Inject
    private BusMapper busMapper;

    @Inject
    private Utils utils;

    @Inject
    private NavigationParameterContext navigationParameterContext;

    @Inject
    private GroupManager groupManager;

    public Class<? extends ViewConfig> createTicket(Bus bus) {
        ticketDto.setBusDto((BusDto) busMapper.mapResult(bus));
        return utils.isGroup() ? null : Access.Seat.class;
    }

    public Class<? extends ViewConfig> submitBusLine() {
        if (lineDto != null && lineDto.getDeparture() != null && lineDto.getArrival() != null
            && !lineDto.getDeparture().equals(lineDto.getArrival())) {
            return Access.Date.class;
        }
        return null;
    }

    public Class<? extends ViewConfig> submitSeats() {

        if (ticketDto.getBusDto() == null || ticketDto.getBusDto().getLineDto() == null
            || ticketDto.getBusDto().getDate() == null) {
            return Access.BusLine.class;
        }
        if (seatsDto.getChosenSeats() == null || seatsDto.getChosenSeats().size() == 0) {
            return Access.Seat.class;
        }

        List<TicketDto> tickets = new ArrayList<TicketDto>();
        orderDto.setToPay(addTickets(tickets, seatsDto.getChosenSeats()));
        orderDto.setTicketsDto(tickets);

        return utils.isGroup() ? null : Access.Overview.class;
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
        Long orderId = orderRepository.save(orderDto).getId();

        navigationParameterContext.addPageParameter("orderId", orderId);
        navigationParameterContext.addPageParameter("price", orderDto.getToPay());
        groupManager.resetBusLine();

        return Pages.Ordered.class;
    }

    public Class<? extends ViewConfig> backToBusLine() {
        return Access.BusLine.class;
    }

    public Class<? extends ViewConfig> backToDate() {
        return Access.Date.class;
    }

    public Class<? extends ViewConfig> backToSeat() {
        return Access.Seat.class;
    }

    public Class<? extends ViewConfig> toSinglePage() {
        return Pages.Group.SinglePage.class;
    }
}
