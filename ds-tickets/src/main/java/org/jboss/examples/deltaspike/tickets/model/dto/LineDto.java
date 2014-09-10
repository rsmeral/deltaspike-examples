package org.jboss.examples.deltaspike.tickets.model.dto;

import java.io.Serializable;

import javax.inject.Named;

import org.apache.deltaspike.core.api.scope.ViewAccessScoped;

@Named
@ViewAccessScoped
public class LineDto implements Serializable {

    private static final long serialVersionUID = -6837044939654258118L;

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
