package org.jboss.examples.deltaspike.expensetracker.view;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.apache.deltaspike.core.api.config.view.ViewConfig;
import org.apache.deltaspike.core.api.config.view.metadata.ViewConfigResolver;
import org.apache.deltaspike.core.api.exception.control.BeforeHandles;
import org.apache.deltaspike.core.api.exception.control.ExceptionHandler;
import org.apache.deltaspike.core.api.exception.control.event.ExceptionEvent;
import org.apache.deltaspike.security.api.authorization.ErrorViewAwareAccessDeniedException;

/**
 * A helper which holds the view that was requested and resulted in an access
 * denied exception.
 */
@SessionScoped
@ExceptionHandler
public class AccessDeniedViewHolder implements Serializable {

    @Inject
    private FacesContext faces;

    @Inject
    private ViewConfigResolver viewConfigResolver;

    private Class<? extends ViewConfig> deniedView;

    public void rememberDeniedView(@BeforeHandles ExceptionEvent<ErrorViewAwareAccessDeniedException> evt) {
        deniedView = viewConfigResolver.getViewConfigDescriptor(faces.getViewRoot().getViewId()).getConfigClass();
        evt.handledAndContinue();
    }

    public Class<? extends ViewConfig> getDeniedView() {
        return deniedView;
    }

    public void resetDeniedView() {
        this.deniedView = null;
    }

    public boolean hasDeniedView() {
        return deniedView != null;
    }

}
