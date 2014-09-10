package org.jboss.examples.deltaspike.tickets.model.mapper;

import java.util.List;

import javax.inject.Inject;

import org.apache.deltaspike.data.api.mapping.SimpleQueryInOutMapperBase;
import org.jboss.examples.deltaspike.tickets.model.Order;
import org.jboss.examples.deltaspike.tickets.model.dto.OrderDto;
import org.jboss.examples.deltaspike.tickets.model.dto.TicketDto;

public class OrderMapper extends SimpleQueryInOutMapperBase<Order, OrderDto> {

    @Inject
    private TicketMapper ticketMapper;

    @Override
    protected Object getPrimaryKey(OrderDto dto) {
        return dto.getId();
    }

    @Override
    protected OrderDto toDto(Order entity) {
        return new OrderDto(entity.getId(), (List<TicketDto>) ticketMapper.mapResultList(entity.getTickets()),
            entity.getPaymentType(),
            entity.isPaid(), entity.getToPay());
    }

    @Override
    protected Order toEntity(Order entity, OrderDto dto) {
        entity.setPaid(dto.getPaid());
        entity.setTickets(ticketMapper.toEntityList(dto.getTicketsDto()));
        entity.setPaymentType(dto.getPaymentType());
        entity.setToPay(dto.getToPay());
        return entity;
    }
}
