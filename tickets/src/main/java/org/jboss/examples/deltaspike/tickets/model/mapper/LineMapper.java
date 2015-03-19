package org.jboss.examples.deltaspike.tickets.model.mapper;

import org.apache.deltaspike.data.api.mapping.SimpleQueryInOutMapperBase;
import org.jboss.examples.deltaspike.tickets.model.Line;
import org.jboss.examples.deltaspike.tickets.model.dto.LineDto;
import org.jboss.examples.deltaspike.tickets.model.dto.access.LineDtoAccess;

public class LineMapper extends SimpleQueryInOutMapperBase<Line, LineDto> {

    @Override
    protected Object getPrimaryKey(LineDto dto) {
        return dto.getId();
    }

    @Override
    protected LineDto toDto(Line entity) {
        LineDto lineDto = new LineDtoAccess(entity.getDeparture(), entity.getArrival(), entity.getPrice());
        lineDto.setId(entity.getId());
        return lineDto;
    }

    @Override
    protected Line toEntity(Line entity, LineDto dto) {
        entity.setDeparture(dto.getDeparture());
        entity.setArrival(dto.getArrival());
        entity.setPrice(dto.getPrice());
        entity.setId(dto.getId());

        return entity;
    }

}
