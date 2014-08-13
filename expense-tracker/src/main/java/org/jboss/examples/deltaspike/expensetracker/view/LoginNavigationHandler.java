package org.jboss.examples.deltaspike.expensetracker.view;

import org.jboss.examples.deltaspike.expensetracker.view.config.SecuredPages;
import org.jboss.examples.deltaspike.expensetracker.view.config.Login;
import javax.enterprise.event.Observes;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.apache.deltaspike.core.api.config.view.navigation.ViewNavigationHandler;
import org.picketlink.authentication.event.LoggedInEvent;
import org.picketlink.authentication.event.LoginFailedEvent;

public class LoginNavigationHandler {

    @Inject
    private ViewNavigationHandler view;

    @Inject
    private FacesContext faces;
    
    public void redirectHomeOnLogin(@Observes LoggedInEvent loggedInEvent) {
        view.navigateTo(SecuredPages.Reports.class);
    }
    
    public void redirectBackOnFailedLogin(@Observes LoginFailedEvent loggedInEvent) {
        faces.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login failed.", null));
        view.navigateTo(Login.class);
    }
    
}
