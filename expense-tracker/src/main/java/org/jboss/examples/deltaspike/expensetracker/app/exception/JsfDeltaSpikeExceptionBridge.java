package org.jboss.examples.deltaspike.expensetracker.app.exception;

import java.util.Iterator;

import javax.enterprise.inject.spi.BeanManager;
import javax.faces.FacesException;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.event.ExceptionQueuedEvent;

import org.apache.deltaspike.core.api.exception.control.event.ExceptionToCatchEvent;
import org.apache.deltaspike.core.api.provider.BeanManagerProvider;

/**
 * This class handles JSF exceptions and bridges them over to DeltaSpike's
 * exception handling system.
 *
 * @author <a href="mailto:benevides@redhat.com">Rafael Benevides</a>
 *
 */
public class JsfDeltaSpikeExceptionBridge extends ExceptionHandlerWrapper {

    private final BeanManager beanManager;

    private final ExceptionHandler wrapped;

    public JsfDeltaSpikeExceptionBridge(final ExceptionHandler wrapped) {
        this.wrapped = wrapped;
        this.beanManager = BeanManagerProvider.getInstance().getBeanManager();
    }

    @Override
    public ExceptionHandler getWrapped() {
        return this.wrapped;
    }

    @Override
    public void handle() throws FacesException {
        Iterator<ExceptionQueuedEvent> it = getUnhandledExceptionQueuedEvents().iterator();
        while (it.hasNext()) {
            try {
                ExceptionQueuedEvent evt = it.next();
                ExceptionToCatchEvent etce = new ExceptionToCatchEvent(evt.getContext().getException());
                beanManager.fireEvent(etce);
            } finally {
                it.remove();
            }

        }
        getWrapped().handle();
    }
}
