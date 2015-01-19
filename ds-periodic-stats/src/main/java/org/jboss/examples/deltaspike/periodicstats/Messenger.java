package org.jboss.examples.deltaspike.periodicstats;

import java.util.Date;
import javax.enterprise.inject.Model;
import javax.inject.Inject;

@Model
public class Messenger {

    @Inject
    private MessageInfoBuffer buffer;

    private String message;

    public void send() {
        buffer.add(new MessageInfo(message, new Date()));
        message = null;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
