package org.jboss.examples.deltaspike.tickets.model.dto;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import org.apache.deltaspike.core.api.scope.ConversationGroup;
import org.jboss.examples.deltaspike.tickets.model.dto.access.BusDtoAccess;
import org.jboss.examples.deltaspike.tickets.model.dto.access.LineDtoAccess;
import org.jboss.examples.deltaspike.tickets.model.dto.access.OrderDtoAccess;
import org.jboss.examples.deltaspike.tickets.model.dto.access.SeatsDtoAccess;
import org.jboss.examples.deltaspike.tickets.model.dto.access.TicketDtoAccess;
import org.jboss.examples.deltaspike.tickets.model.dto.group.BusDtoGroup;
import org.jboss.examples.deltaspike.tickets.model.dto.group.BusTicketsWizzardGroup;
import org.jboss.examples.deltaspike.tickets.model.dto.group.LineDtoGroup;
import org.jboss.examples.deltaspike.tickets.model.dto.group.OrderDtoGroup;
import org.jboss.examples.deltaspike.tickets.model.dto.group.SeatsDtoGroup;
import org.jboss.examples.deltaspike.tickets.model.dto.group.TicketDtoGroup;
import org.jboss.examples.deltaspike.tickets.util.Utils;

public class DtoResolver {

    @Inject
    private Utils utils;

    @Inject
    private BusDtoAccess busDtoAccess;

    @Inject
    @ConversationGroup(BusTicketsWizzardGroup.class)
    private BusDtoGroup busDtoGroup;

    @Inject
    private LineDtoAccess lineDtoAccess;

    @Inject
    @ConversationGroup(BusTicketsWizzardGroup.class)
    private LineDtoGroup lineDtoGroup;

    @Inject
    private OrderDtoAccess orderDtoAccess;

    @Inject
    @ConversationGroup(BusTicketsWizzardGroup.class)
    private OrderDtoGroup orderDtoGroup;

    @Inject
    private SeatsDtoAccess seatsDtoAccess;

    @Inject
    @ConversationGroup(BusTicketsWizzardGroup.class)
    private SeatsDtoGroup seatsDtoGroup;

    @Inject
    private TicketDtoAccess ticketDtoAccess;

    @Inject
    @ConversationGroup(BusTicketsWizzardGroup.class)
    private TicketDtoGroup ticketDtoGroup;

    @Produces
    @Produced
    public BusDto getBusDto() {
        if (utils.isGroup()) {
            return busDtoGroup;
        } else {
            return busDtoAccess;
        }
    }

    @Produces
    @Produced
    public LineDto getLineDto() {
        if (utils.isGroup()) {
            return lineDtoGroup;
        } else {
            return lineDtoAccess;
        }
    }

    @Produces
    @Produced
    public OrderDto getOrderDto() {
        if (utils.isGroup()) {
            return orderDtoGroup;
        } else {
            return orderDtoAccess;
        }
    }

    @Produces
    @Produced
    public SeatsDto getSeatsDto() {
        if (utils.isGroup()) {
            return seatsDtoGroup;
        } else {
            return seatsDtoAccess;
        }
    }

    @Produces
    @Produced
    public TicketDto getTicketDto() {
        if (utils.isGroup()) {
            return ticketDtoGroup;
        } else {
            return ticketDtoAccess;
        }
    }
}
