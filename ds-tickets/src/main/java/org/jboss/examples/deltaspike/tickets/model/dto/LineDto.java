package org.jboss.examples.deltaspike.tickets.model.dto;

import java.io.Serializable;

public abstract class LineDto implements Serializable {

    private Long id;
    private String departure;
    private String arrival;
    private int price;

    public LineDto() {
    }

    public LineDto(String departure, String arrival, int price) {
        super();
        this.departure = departure;
        this.arrival = arrival;
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDeparture() {
        // System.err.println("getDeparture " + this);
        return departure;
    }

    public void setDeparture(String departure) {
        // System.err.println("setDeparture " + this);
        this.departure = departure;
    }

    public String getArrival() {
        // System.err.println("getArrival " + this);
        return arrival;
    }

    public void setArrival(String arrival) {
        // System.err.println("setArrival " + this);
        this.arrival = arrival;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

}
