package org.jboss.examples.deltaspike.expensetracker.app.exception;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.apache.deltaspike.core.api.exception.control.ExceptionHandler;
import org.apache.deltaspike.core.api.exception.control.Handles;
import org.apache.deltaspike.core.api.exception.control.event.ExceptionEvent;
import org.apache.deltaspike.core.api.exclude.Exclude;
import org.apache.deltaspike.core.api.projectstage.ProjectStage;
import org.apache.deltaspike.security.api.authorization.AccessDeniedException;

/**
 * During development, exceptions won't get handled and will be shown directly.
 */
@Exclude(ifProjectStage = ProjectStage.Development.class)
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
    
    public void handleOtherExceptions(@Handles(ordinal = 100) ExceptionEvent<Exception> evt) {
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, evt.getException().getMessage(), null));
        evt.handledAndContinue();
    }

}
