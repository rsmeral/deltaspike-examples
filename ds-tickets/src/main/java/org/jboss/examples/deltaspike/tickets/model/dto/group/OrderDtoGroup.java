package org.jboss.examples.deltaspike.tickets.model.dto.group;

import java.util.List;

import javax.inject.Named;

import org.apache.deltaspike.core.api.scope.ConversationGroup;
import org.apache.deltaspike.core.api.scope.GroupedConversationScoped;
import org.jboss.examples.deltaspike.tickets.model.dto.OrderDto;
import org.jboss.examples.deltaspike.tickets.model.dto.TicketDto;

@Named
@GroupedConversationScoped
@ConversationGroup(BusTicketsWizzardGroup.class)
public class OrderDtoGroup extends OrderDto {

    public OrderDtoGroup() {
    }

    public OrderDtoGroup(Long id, List<TicketDto> ticketsDto, String paymentType, boolean paid, Double toPay) {
        super(id, ticketsDto, paymentType, paid, toPay);
    }

}
