package org.jboss.examples.deltaspike.expensetracker.app.exception;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.apache.deltaspike.core.api.exception.control.ExceptionHandler;
import org.apache.deltaspike.core.api.exception.control.Handles;
import org.apache.deltaspike.core.api.exception.control.event.ExceptionEvent;
import org.apache.deltaspike.security.api.authorization.AccessDeniedException;

@ExceptionHandler
public class ExceptionHandlers {

    @Inject
    private FacesContext facesContext;

    public void handleAppException(@Handles ExceptionEvent<ApplicationException> evt) {
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, evt.getException().getMessage(), null));
        evt.handledAndContinue();
    }

    public void handleAccessDeniedException(@Handles ExceptionEvent<AccessDeniedException> evt) {
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, evt.getException().getMessage(), null));
        evt.handledAndContinue();
    }

}
