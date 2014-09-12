package org.jboss.examples.deltaspike.tickets.model.dto;

import java.io.Serializable;
import java.util.List;

public class OrderDto implements Serializable {

    private static final long serialVersionUID = 4011295393734808311L;
    private Long id;
    private List<TicketDto> ticketsDto;
    private String paymentType;
    private boolean paid;
    private Double toPay;

    public OrderDto() {
    }

    public OrderDto(Long id, List<TicketDto> ticketsDto, String paymentType, boolean paid, Double toPay) {
        super();
        this.id = id;
        this.ticketsDto = ticketsDto;
        this.paymentType = paymentType;
        this.paid = paid;
        this.toPay = toPay;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<TicketDto> getTicketsDto() {
        return ticketsDto;
    }

    public void setTicketsDto(List<TicketDto> ticketsDto) {
        this.ticketsDto = ticketsDto;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public boolean getPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public Double getToPay() {
        return toPay;
    }

    public void setToPay(Double toPay) {
        this.toPay = toPay;
    }

}
