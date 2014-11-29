package org.jboss.examples.deltaspike.servletobjects;

import org.jboss.examples.deltaspike.servletobjects.servlet.RequestResponseTimer;
import org.jboss.examples.deltaspike.servletobjects.servlet.HeaderParam;
import org.jboss.examples.deltaspike.servletobjects.servlet.ContextPath;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import org.apache.deltaspike.core.api.common.DeltaSpike;

@Model
public class RequestInfoBean {

    @Inject
    @HeaderParam("Host")
    private String host;

    @Inject
    @ContextPath
    private String ctxPath;

    @Inject
    @DeltaSpike
    private HttpServletRequest req;

    @Inject
    private RequestResponseTimer timer;

    public long getLastRequestDuration() {
        return timer.getLastRequestDuration();
    }

    /*
     * The request URL must contain the host and the context path.
     */
    public boolean isOk() {
        String reqUrl = req.getRequestURL().toString();
        return host != null && !host.isEmpty() && reqUrl.contains(host)
                && ctxPath != null && reqUrl.contains(ctxPath);
    }

}
