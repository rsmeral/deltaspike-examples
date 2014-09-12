package org.jboss.examples.deltaspike.tickets.model.dto.group;

import javax.inject.Named;

import org.apache.deltaspike.core.api.scope.ConversationGroup;
import org.apache.deltaspike.core.api.scope.GroupedConversationScoped;
import org.jboss.examples.deltaspike.tickets.model.dto.LineDto;

@Named
@GroupedConversationScoped
@ConversationGroup(BusTicketsWizzardGroup.class)
public class LineDtoGroup extends LineDto {

    public LineDtoGroup(String departure, String arrival, int price) {
        super(departure, arrival, price);
    }

    public LineDtoGroup() {
    }

}
