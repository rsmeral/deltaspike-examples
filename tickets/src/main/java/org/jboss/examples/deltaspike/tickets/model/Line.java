package org.jboss.examples.deltaspike.tickets.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Line implements Serializable {

    private static final long serialVersionUID = -3606817363522150334L;

    @Id
    @GeneratedValue
    private Long id;

    private String departure;

    private String arrival;

    private int price;

    public Line() {
    }

    public Line(String departure, String arrival, int price) {
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

}
