package org.jboss.examples.deltaspike.tickets.model.dto.access;

import java.util.List;

import javax.inject.Named;

import org.apache.deltaspike.core.api.scope.ViewAccessScoped;
import org.jboss.examples.deltaspike.tickets.model.dto.SeatsDto;

@Named
@ViewAccessScoped
public class SeatsDtoAccess extends SeatsDto {

    public SeatsDtoAccess() {
    }

    public SeatsDtoAccess(List<String> chosenSeats) {
        super(chosenSeats);
    }

}
