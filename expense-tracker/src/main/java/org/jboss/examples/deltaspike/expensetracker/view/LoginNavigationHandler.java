package org.jboss.examples.deltaspike.expensetracker.view;

import javax.enterprise.event.Observes;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.apache.deltaspike.core.api.config.view.navigation.ViewNavigationHandler;
import org.jboss.examples.deltaspike.expensetracker.app.message.AppMessages;
import org.picketlink.authentication.event.AlreadyLoggedInEvent;
import org.picketlink.authentication.event.LoggedInEvent;
import org.picketlink.authentication.event.LoginFailedEvent;
import org.picketlink.authentication.event.PostLoggedOutEvent;

public class LoginNavigationHandler {

    @Inject
    private ViewNavigationHandler view;

    @Inject
    private FacesContext faces;
    
    @Inject
    private AppMessages msg;
    
    public void redirectHomeOnLogin(@Observes LoggedInEvent loggedInEvent) {
        view.navigateTo(Pages.Secured.Home.class);
    }
    
    public void redirectHomeWhenAlreadyLoggedIn(@Observes AlreadyLoggedInEvent loggedInEvent) {
        view.navigateTo(Pages.Secured.Home.class);
    }
    
    public void redirectBackOnFailedLogin(@Observes LoginFailedEvent loggedInEvent) {
        faces.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msg.loginFailed(), null));
        view.navigateTo(Pages.Login.class);
    }
    
    public void redirectToLoginOnLogout(@Observes PostLoggedOutEvent loggedInEvent) {
        faces.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msg.loggedOutSuccessfully(), null));
        view.navigateTo(Pages.Login.class);
    }
    
}
