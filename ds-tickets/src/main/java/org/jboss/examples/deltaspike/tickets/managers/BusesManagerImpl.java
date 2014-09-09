package org.jboss.examples.deltaspike.tickets.managers;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

import org.jboss.examples.deltaspike.tickets.model.Bus;
import org.jboss.examples.deltaspike.tickets.model.Line;
import org.jboss.examples.deltaspike.tickets.repositories.BusReporitory;

@Model
public class BusesManagerImpl implements BusesManager {

    @Inject
    private BusReporitory busReporitory;

    @Inject
    private Line line;

    public List<Bus> getBuses() {
        if (line.getDeparture() == null || line.getArrival() == null){
            return new ArrayList<Bus>();
        }
        List<Bus> buses = busReporitory.getDates(line.getDeparture(), line.getArrival());
        return buses;
    }

}
