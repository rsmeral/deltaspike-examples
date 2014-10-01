package org.jboss.examples.deltaspike.tickets.test;

import java.util.List;

public class Order {

    private String departure;
    private String arrival;
    private String date;
    private List<String> seats;

    public Order() {
        // TODO Auto-generated constructor stub
    }

    public Order(String departure, String arrival, String date, List<String> seats) {
        super();
        this.departure = departure;
        this.arrival = arrival;
        this.date = date;
        this.seats = seats;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getArrival() {
        return arrival;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<String> getSeats() {
        return seats;
    }

    public void setSeats(List<String> seats) {
        this.seats = seats;
    }

}
