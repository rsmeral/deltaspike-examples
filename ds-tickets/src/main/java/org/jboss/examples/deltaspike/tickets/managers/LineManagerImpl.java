package org.jboss.examples.deltaspike.tickets.managers;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

import org.jboss.examples.deltaspike.tickets.repositories.LineReporitory;

@Model
public class LineManagerImpl {

    @Inject
    private LineReporitory lineReporitory;

    public List<String> getAllDepartures() {
        return lineReporitory.getAllDepartures();
    }

    public List<String> getAllArrivals(String departure) {
        if (departure == null) {
            return new ArrayList<String>();
        }
        return lineReporitory.getAllArrivals(departure);
    }

}
