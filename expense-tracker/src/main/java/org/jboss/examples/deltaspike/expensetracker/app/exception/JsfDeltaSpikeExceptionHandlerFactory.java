package org.jboss.examples.deltaspike.expensetracker.app.exception;

import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;

import org.apache.deltaspike.core.api.exception.control.event.ExceptionToCatchEvent;

/**
 * This class is registered in src/main/webapp/WEB-INF/faces-config.xml file.
 * 
 * It creates the {@link JsfDeltaSpikeExceptionBridge} class that is responsible to fire the {@link ExceptionToCatchEvent}
 * 
 * @author <a href="mailto:benevides@redhat.com">Rafael Benevides</a>
 * 
 */
public class JsfDeltaSpikeExceptionHandlerFactory extends ExceptionHandlerFactory {

    private final javax.faces.context.ExceptionHandlerFactory parent;

    public JsfDeltaSpikeExceptionHandlerFactory(final ExceptionHandlerFactory parent) {
        this.parent = parent;
    }

    @Override
    public ExceptionHandler getExceptionHandler() {
        return new JsfDeltaSpikeExceptionBridge(parent.getExceptionHandler());
    }

}