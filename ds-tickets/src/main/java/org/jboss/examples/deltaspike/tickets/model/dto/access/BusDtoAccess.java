package org.jboss.examples.deltaspike.tickets.model.dto.access;

import java.util.Date;

import org.apache.deltaspike.core.api.scope.ViewAccessScoped;
import org.jboss.examples.deltaspike.tickets.model.dto.BusDto;
import org.jboss.examples.deltaspike.tickets.model.dto.LineDto;

@ViewAccessScoped
public class BusDtoAccess extends BusDto {

    public BusDtoAccess() {
    }

    public BusDtoAccess(LineDto lineDto, Date date) {
        super(lineDto, date);
    }

}
