package org.jboss.examples.deltaspike.tickets.model.dto.access;

import javax.inject.Named;

import org.apache.deltaspike.core.api.scope.ViewAccessScoped;
import org.jboss.examples.deltaspike.tickets.model.dto.BusDto;
import org.jboss.examples.deltaspike.tickets.model.dto.TicketDto;

@Named
@ViewAccessScoped
public class TicketDtoAccess extends TicketDto {

    public TicketDtoAccess() {
    }

    public TicketDtoAccess(BusDto busDto, String seatNumber, boolean isInFirstClass) {
        super(busDto, seatNumber, isInFirstClass);
    }

}
