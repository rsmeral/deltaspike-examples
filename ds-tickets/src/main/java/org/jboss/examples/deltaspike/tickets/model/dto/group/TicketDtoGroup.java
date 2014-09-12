package org.jboss.examples.deltaspike.tickets.model.dto.group;

import javax.inject.Named;

import org.apache.deltaspike.core.api.scope.ConversationGroup;
import org.apache.deltaspike.core.api.scope.GroupedConversationScoped;
import org.jboss.examples.deltaspike.tickets.model.dto.BusDto;
import org.jboss.examples.deltaspike.tickets.model.dto.TicketDto;

@Named
@GroupedConversationScoped
@ConversationGroup(BusTicketsWizzardGroup.class)
public class TicketDtoGroup extends TicketDto {

    public TicketDtoGroup() {
    }

    public TicketDtoGroup(BusDto busDto, String seatNumber, boolean isInFirstClass) {
        super(busDto, seatNumber, isInFirstClass);
    }

}
