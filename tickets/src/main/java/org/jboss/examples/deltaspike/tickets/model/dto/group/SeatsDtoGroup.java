package org.jboss.examples.deltaspike.tickets.model.dto.group;

import java.util.List;

import javax.inject.Named;

import org.apache.deltaspike.core.api.scope.ConversationGroup;
import org.apache.deltaspike.core.api.scope.GroupedConversationScoped;
import org.jboss.examples.deltaspike.tickets.model.dto.SeatsDto;

@Named
@GroupedConversationScoped
@ConversationGroup(BusTicketsWizzardGroup.class)
public class SeatsDtoGroup extends SeatsDto {

    public SeatsDtoGroup() {
    }

    public SeatsDtoGroup(List<String> chosenSeats) {
        super(chosenSeats);
    }

}
