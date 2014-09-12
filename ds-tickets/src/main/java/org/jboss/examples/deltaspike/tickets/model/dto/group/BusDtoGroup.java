package org.jboss.examples.deltaspike.tickets.model.dto.group;

import java.util.Date;

import org.apache.deltaspike.core.api.scope.ConversationGroup;
import org.apache.deltaspike.core.api.scope.GroupedConversationScoped;
import org.jboss.examples.deltaspike.tickets.model.dto.BusDto;
import org.jboss.examples.deltaspike.tickets.model.dto.LineDto;

@GroupedConversationScoped
@ConversationGroup(BusTicketsWizzardGroup.class)
public class BusDtoGroup extends BusDto {

    public BusDtoGroup() {
    }

    public BusDtoGroup(LineDto lineDto, Date date) {
        super(lineDto, date);
    }

}
