package org.jboss.examples.deltaspike.tickets.model.dto;

import java.io.Serializable;
import java.util.Date;

public class BusDto implements Serializable {

    private static final long serialVersionUID = 5502302156634334210L;

    private Long id;
    private LineDto lineDto;
    private Date date;

    public BusDto() {
    }

    public BusDto(LineDto lineDto, Date date) {
        super();
        this.lineDto = lineDto;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LineDto getLineDto() {
        return lineDto;
    }

    public void setLineDto(LineDto line) {
        this.lineDto = line;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
