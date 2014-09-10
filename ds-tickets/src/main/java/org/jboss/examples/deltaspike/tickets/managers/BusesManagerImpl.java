package org.jboss.examples.deltaspike.tickets.managers;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

import org.jboss.examples.deltaspike.tickets.model.Bus;
import org.jboss.examples.deltaspike.tickets.model.dto.LineDto;
import org.jboss.examples.deltaspike.tickets.repositories.BusReporitory;

@Model
public class BusesManagerImpl implements BusesManager {

    @Inject
    private BusReporitory busReporitory;

    @Inject
    private LineDto lineDto;

    public List<Bus> getBuses() {
        if (lineDto.getDeparture() == null || lineDto.getArrival() == null) {
            return new ArrayList<Bus>();
        }
        List<Bus> buses = busReporitory.getDates(lineDto.getDeparture(), lineDto.getArrival());
        return buses;
    }

}
