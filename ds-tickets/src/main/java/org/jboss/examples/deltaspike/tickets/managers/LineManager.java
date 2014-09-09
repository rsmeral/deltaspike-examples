package org.jboss.examples.deltaspike.tickets.managers;

import java.util.List;

public interface LineManager {

    public List<String> getAllDepartures();

    public List<String> getAllArrivals(String departure);

}
