package org.jboss.examples.deltaspike.tickets.model;

import java.io.Serializable;
import java.util.List;

import javax.inject.Named;

import org.apache.deltaspike.core.api.scope.ViewAccessScoped;

@ViewAccessScoped
@Named
public class Order implements Serializable {

    private static final long serialVersionUID = 4011295393734808311L;
    private Long id;
    private List<Ticket> tickets;
    private String paymentType;
    private boolean paid;
    private Double toPay;

    public Order() {
    }

    public Order(Long id, List<Ticket> tickets, String paymentType, boolean paid, Double toPay) {
        super();
        this.id = id;
        this.tickets = tickets;
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

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
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
