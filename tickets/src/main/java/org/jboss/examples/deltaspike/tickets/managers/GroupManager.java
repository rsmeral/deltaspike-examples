package org.jboss.examples.deltaspike.tickets.managers;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

import org.apache.deltaspike.core.spi.scope.conversation.GroupedConversationManager;
import org.jboss.examples.deltaspike.tickets.model.dto.group.BusTicketsWizzardGroup;
import org.jboss.examples.deltaspike.tickets.model.dto.group.BusTicketsWizzardGroup.DateSubgroup;
import org.jboss.examples.deltaspike.tickets.model.dto.group.BusTicketsWizzardGroup.SeatsSubgroup;

@Model
public class GroupManager {

    @Inject
    private GroupedConversationManager conversationManager;

    public void resetBusLine() {
        conversationManager.closeConversationGroup(BusTicketsWizzardGroup.class);
    }

    public void resetDate() {
        conversationManager.closeConversationGroup(DateSubgroup.class);
    }

    public void resetSeats() {
        conversationManager.closeConversationGroup(SeatsSubgroup.class);
    }

}
