package org.jboss.examples.deltaspike.servletobjects.servlet;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.deltaspike.core.api.lifecycle.Destroyed;
import org.apache.deltaspike.core.api.lifecycle.Initialized;

@SessionScoped
public class RequestResponseTimer implements Serializable {

    private Long start = 0l;
    private Long duration = 0l;

    public void requestStart(@Observes @Initialized HttpServletRequest req) {
        start = System.currentTimeMillis();
    }

    public void responseEnd(@Observes @Destroyed HttpServletResponse resp) {
        duration = System.currentTimeMillis() - start;
    }

    public long getLastRequestDuration() {
        return duration;
    }

}
