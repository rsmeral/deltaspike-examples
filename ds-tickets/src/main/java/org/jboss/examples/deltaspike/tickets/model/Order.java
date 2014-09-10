package org.jboss.examples.deltaspike.tickets.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "ticketsorder")
public class Order implements Serializable {

    private static final long serialVersionUID = -6230727467210575441L;

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Ticket> tickets;
    private String paymentType;
    private Boolean paid;
    private Double toPay;

    public Order() {
    }

    public Order(List<Ticket> tickets, String paymentType, boolean paid, double toPay) {
        super();
        this.tickets = tickets;
        this.paymentType = paymentType;
        this.paid = paid;
        this.toPay = toPay;
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

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public double getToPay() {
        return toPay;
    }

    public void setToPay(double toPay) {
        this.toPay = toPay;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
