package org.jboss.examples.deltaspike.tickets.model.dto;

import java.io.Serializable;

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
        // System.err.println("getBusDto " + "busDto " + this);
        return busDto;
    }

    public void setBusDto(BusDto busDto) {
        // System.err.println("setBusDto " + "busDto " + this);
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
