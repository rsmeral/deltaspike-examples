package org.jboss.examples.deltaspike.tickets.model;

import org.apache.deltaspike.data.api.mapping.SimpleQueryInOutMapperBase;

public class OrderMapper extends SimpleQueryInOutMapperBase<OrderEntity, Order> {

    @Override
    protected Object getPrimaryKey(Order dto) {
        return dto.getId();
    }

    @Override
    protected Order toDto(OrderEntity entity) {
        return new Order(entity.getId(), entity.getTickets(), entity.getPaymentType(), entity.isPaid(), entity.getToPay());
    }

    @Override
    protected OrderEntity toEntity(OrderEntity entity, Order dto) {
        entity.setPaid(dto.getPaid());
        entity.setTickets(dto.getTickets());
        entity.setPaymentType(dto.getPaymentType());
        entity.setToPay(dto.getToPay());
        return entity;
    }
}
