package org.jboss.examples.deltaspike.tickets.model.dto.access;

import javax.inject.Named;

import org.apache.deltaspike.core.api.scope.ViewAccessScoped;
import org.jboss.examples.deltaspike.tickets.model.dto.LineDto;

@Named
@ViewAccessScoped
public class LineDtoAccess extends LineDto {

    public LineDtoAccess(String departure, String arrival, int price) {
        super(departure, arrival, price);
    }

    public LineDtoAccess() {
    }

}
