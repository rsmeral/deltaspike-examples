package org.jboss.examples.deltaspike.tickets.model;

import java.io.Serializable;

import javax.inject.Named;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.apache.deltaspike.core.api.scope.ViewAccessScoped;

@Named
@ViewAccessScoped
@Entity
public class Ticket implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    private static final long serialVersionUID = 6681604968417252681L;

    @ManyToOne
    private Bus bus;
    private String seatNumber;
    private boolean isInFirstClass;

    public Ticket() {
    }

    public Ticket(Bus bus, String seatNumber, boolean isInFirstClass) {
        super();
        this.bus = bus;
        this.seatNumber = seatNumber;
        this.isInFirstClass = isInFirstClass;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Bus getBus() {
        return bus;
    }

    public void setBus(Bus bus) {
        this.bus = bus;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public boolean isInFirstClass() {
        return isInFirstClass;
    }

    public void setInFirstClass(boolean isInFirstClass) {
        this.isInFirstClass = isInFirstClass;
    }

}
