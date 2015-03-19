package org.jboss.examples.deltaspike.tickets.model.dto;

import java.io.Serializable;
import java.util.List;

public class SeatsDto implements Serializable {

    private static final long serialVersionUID = 2172567447790811127L;
    private List<String> chosenSeats;

    public SeatsDto() {
    }

    public SeatsDto(List<String> chosenSeats) {
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
