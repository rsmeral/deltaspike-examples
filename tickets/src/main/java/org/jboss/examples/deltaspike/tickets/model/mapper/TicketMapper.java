package org.jboss.examples.deltaspike.tickets.model.mapper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.deltaspike.data.api.mapping.SimpleQueryInOutMapperBase;
import org.jboss.examples.deltaspike.tickets.model.Bus;
import org.jboss.examples.deltaspike.tickets.model.Ticket;
import org.jboss.examples.deltaspike.tickets.model.dto.TicketDto;

public class TicketMapper extends SimpleQueryInOutMapperBase<Ticket, TicketDto> {

    @Inject
    private BusMapper busMapper;

    @Override
    protected Object getPrimaryKey(TicketDto dto) {
        return dto.getId();
    }

    @Override
    protected TicketDto toDto(Ticket entity) {
        TicketDto ticketDto = new TicketDto(busMapper.toDto(entity.getBus()), entity.getSeatNumber(), entity.isInFirstClass());
        ticketDto.setId(entity.getId());
        return ticketDto;
    }

    @Override
    protected Ticket toEntity(Ticket entity, TicketDto dto) {
        entity.setBus(busMapper.toEntity(new Bus(), dto.getBusDto()));
        entity.setSeatNumber(dto.getSeatNumber());
        entity.setInFirstClass(dto.isInFirstClass());
        entity.setId(dto.getId());

        return entity;
    }

    public List<Ticket> toEntityList(List<TicketDto> dtoList) {
        ArrayList<Ticket> entityList = new ArrayList<Ticket>(dtoList.size());
        for (TicketDto ticket : dtoList) {
            entityList.add(toEntity(new Ticket(), ticket));
        }
        return entityList;
    }

}
