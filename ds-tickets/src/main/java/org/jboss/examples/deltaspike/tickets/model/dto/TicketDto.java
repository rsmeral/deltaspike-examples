package org.jboss.examples.deltaspike.tickets.model.dto;

import java.io.Serializable;

import javax.inject.Named;

import org.apache.deltaspike.core.api.scope.ViewAccessScoped;

@Named
@ViewAccessScoped
public class TicketDto implements Serializable {

    private static final long serialVersionUID = -8351858997978049478L;

    private Long id;
    private BusDto busDto;
    private String seatNumber;
    private boolean isInFirstClass;

    public TicketDto() {
    }

    public TicketDto(BusDto busDto, String seatNumber, boolean isInFirstClass) {
        super();
        this.busDto = busDto;
        this.seatNumber = seatNumber;
        this.isInFirstClass = isInFirstClass;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BusDto getBusDto() {
        return busDto;
    }

    public void setBusDto(BusDto busDto) {
        this.busDto = busDto;
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
