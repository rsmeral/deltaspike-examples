package org.jboss.examples.deltaspike.tickets.repositories;

import java.util.List;

import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.criteria.CriteriaSupport;
import org.jboss.examples.deltaspike.tickets.model.Bus;
import org.jboss.examples.deltaspike.tickets.model.Bus_;
import org.jboss.examples.deltaspike.tickets.model.Ticket;
import org.jboss.examples.deltaspike.tickets.model.Ticket_;

@Repository(forEntity = Ticket.class)
public abstract class TicketRepository extends AbstractEntityRepository<Ticket, Long> implements CriteriaSupport<Ticket> {

    public List<Ticket> getBusTickets(long busId) {
        return criteria().join(Ticket_.bus, where(Bus.class).eq(Bus_.id, busId)).getResultList();
    }
}
