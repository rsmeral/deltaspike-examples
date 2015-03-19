package org.jboss.examples.deltaspike.periodicstats;

import java.util.Date;

public class MessageInfo {
    private String text;
    
    private Date arrivalTime;

    public MessageInfo() {
    }

    public MessageInfo(String text, Date arrivalTime) {
        this.text = text;
        this.arrivalTime = arrivalTime;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Date arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
}
