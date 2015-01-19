package org.jboss.examples.deltaspike.periodicstats;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MessageInfoBuffer implements Serializable {

    private final List<MessageInfo> buffer = new ArrayList<MessageInfo>();

    public MessageInfoBuffer() {
    }

    public boolean add(MessageInfo e) {
        synchronized (buffer) {
            return buffer.add(e);
        }
    }

    public List<MessageInfo> getBuffer() {
        return Collections.unmodifiableList(buffer);
    }

    public void clear() {
        synchronized (buffer) {
            buffer.clear();
        }
    }

}
