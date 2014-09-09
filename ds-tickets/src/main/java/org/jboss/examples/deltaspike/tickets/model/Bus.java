package org.jboss.examples.deltaspike.tickets.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Bus implements Serializable {

    private static final long serialVersionUID = -5104585034601593966L;

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Line line;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    public Bus() {
    }

    public Bus(Line line, Date date) {
        super();
        this.line = line;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Line getLine() {
        return line;
    }

    public void setLine(Line line) {
        this.line = line;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
