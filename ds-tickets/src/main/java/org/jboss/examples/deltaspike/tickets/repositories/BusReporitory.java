package org.jboss.examples.deltaspike.tickets.repositories;

import java.util.List;

import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.criteria.CriteriaSupport;
import org.jboss.examples.deltaspike.tickets.model.Bus;
import org.jboss.examples.deltaspike.tickets.model.Bus_;
import org.jboss.examples.deltaspike.tickets.model.Line;
import org.jboss.examples.deltaspike.tickets.model.Line_;

@Repository
public abstract class BusReporitory extends AbstractEntityRepository<Bus, Long> implements CriteriaSupport<Bus> {

    public List<Bus> getDates(String departure, String arrival) {
        return criteria().join(Bus_.line, where(Line.class).eq(Line_.departure, departure).eq(Line_.arrival, arrival))
            .orderAsc(Bus_.date).getResultList();
    }
}
