package org.jboss.examples.deltaspike.tickets.model;

import java.io.Serializable;
import java.util.List;

import javax.inject.Named;

import org.apache.deltaspike.core.api.scope.ViewAccessScoped;

@Named
@ViewAccessScoped
public class Seats implements Serializable {

    private static final long serialVersionUID = 2172567447790811127L;
    private List<String> chosenSeats;

    public Seats() {
    }

    public Seats(List<String> chosenSeats) {
        super();
        this.chosenSeats = chosenSeats;
    }

    public List<String> getChosenSeats() {
        return chosenSeats;
    }

    public void setChosenSeats(List<String> chosenSeats) {
        this.chosenSeats = chosenSeats;
    }

}
