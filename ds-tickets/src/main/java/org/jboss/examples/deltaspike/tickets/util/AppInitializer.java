package org.jboss.examples.deltaspike.tickets.util;

import java.io.Serializable;
import java.util.Calendar;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.deltaspike.core.api.config.view.controller.InitView;
import org.jboss.examples.deltaspike.tickets.model.Bus;
import org.jboss.examples.deltaspike.tickets.model.Line;
import org.jboss.examples.deltaspike.tickets.model.Ticket;
import org.jboss.examples.deltaspike.tickets.repositories.BusReporitory;
import org.jboss.examples.deltaspike.tickets.repositories.LineReporitory;
import org.jboss.examples.deltaspike.tickets.repositories.TicketRepository;

@Named
public class AppInitializer implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 7671809179577471311L;

    @Inject
    private LineReporitory lineReporitory;

    @Inject
    private BusReporitory busReporitory;

    @Inject
    private TicketRepository ticketRepository;

    private static boolean initialized = false;

    public AppInitializer() {
    }

    @InitView
    public void initialize() {
        if (initialized) {
            return;
        }
        System.err.println("initialized!!!!!!!!!!");

        createLine("Paris", "Prague", 900);
        createLine("Prague", "Paris", 900);
        // createLine("Paris", "Lisboa", 800);
        // createLine("Lisboa", "Paris", 800);
        // createLine("Roma", "Paris", 700);
        // createLine("Paris", "Roma", 700);
        // createLine("Paris", "Berlin", 900);
        // createLine("Berlin", "Paris", 900);
        // createLine("Oslo", "Paris", 1300);
        // createLine("Berlin", "Oslo", 1200);
        // createLine("Oslo", "Lisboa", 1800);
        initialized = true;
    }

    private Line createLine(String departure, String arrival, int price) {
        Line line = new Line(departure, arrival, price);

        createBusses(lineReporitory.save(line), new int[] { 2015, departure.length(), arrival.length(), 8, 0, 0 }, price / 300,
            price / 100);
        return line;
    }

    private void createBusses(Line line, int[] baseDate, int dateRepetition, int count) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(baseDate[0], baseDate[1], baseDate[2], baseDate[3], baseDate[4], baseDate[5]);

        Bus bus = null;
        for (int i = 0; i < count; i++) {

            bus = new Bus(line, calendar.getTime());
            busReporitory.save(bus);

            createTickets(bus, calendar.get(Calendar.DAY_OF_WEEK), calendar.get(Calendar.WEEK_OF_MONTH));
            calendar.add(Calendar.DATE, dateRepetition);
        }
    }

    private void createTickets(Bus bus, int dayOfWeek, int weekOfMonth) {

        int letterCount = bus.getLine().getDeparture().length() + bus.getLine().getArrival().length();
        int counter = 0;

        for (boolean isFirstClass : new boolean[] { true, false }) {
            for (int i = 0; i < 12; i++) {

                for (String s : new String[] { "A", "B", "C", "D" }) {

                    int myModulo = ++counter % letterCount;
                    if (i == dayOfWeek || i == weekOfMonth || i == dayOfWeek + weekOfMonth || myModulo == 1 || myModulo == 3
                        || myModulo == 5) {

                        String seat = (i + 1) + s;
                        if (!seat.equals("8C") && !seat.equals("8D")) {
                            ticketRepository.save(new Ticket(bus, seat, isFirstClass));
                        }
                    }
                }
            }
        }
    }

    public static boolean isInitialized() {
        return initialized;
    }
}
