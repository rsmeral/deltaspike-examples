package org.jboss.examples.deltaspike.tickets.model.dto.group;

import org.apache.deltaspike.core.api.scope.ConversationSubGroup;

public interface BusTicketsWizzardGroup {

    @ConversationSubGroup(subGroup = { BusDtoGroup.class, TicketDtoGroup.class, SeatsDtoGroup.class, OrderDtoGroup.class })
    public interface DateSubgroup extends BusTicketsWizzardGroup {
    }

    @ConversationSubGroup(subGroup = { SeatsDtoGroup.class, OrderDtoGroup.class })
    public interface SeatsSubgroup extends BusTicketsWizzardGroup {
    }
}
