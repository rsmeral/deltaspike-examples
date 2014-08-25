package org.jboss.examples.deltaspike.expensetracker.view;

import javax.enterprise.event.Observes;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.apache.deltaspike.core.api.config.view.ViewRef;
import org.apache.deltaspike.core.api.config.view.controller.PreRenderView;
import org.apache.deltaspike.core.api.config.view.navigation.ViewNavigationHandler;
import org.jboss.examples.deltaspike.expensetracker.app.resources.AppMessages;
import org.picketlink.Identity;
import org.picketlink.authentication.event.AlreadyLoggedInEvent;
import org.picketlink.authentication.event.LoggedInEvent;
import org.picketlink.authentication.event.LoginFailedEvent;
import org.picketlink.authentication.event.PostLoggedOutEvent;

@ViewRef(config = Login.class)
public class LoginNavigationHandler {

    @Inject
    private ViewNavigationHandler view;

    @Inject
    private FacesContext faces;

    @Inject
    private AppMessages msg;

    @Inject
    private Identity identity;

    public void redirectHomeOnLogin(@Observes LoggedInEvent event) {
        view.navigateTo(SecuredPages.Home.class);
    }

    public void redirectHomeWhenAlreadyLoggedIn(@Observes AlreadyLoggedInEvent event) {
        view.navigateTo(SecuredPages.Home.class);
    }

    public void redirectBackOnFailedLogin(@Observes LoginFailedEvent event) {
        faces.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msg.loginFailed(), null));
        view.navigateTo(Login.class);
    }

    public void invalidateSessionOnLogout(@Observes PostLoggedOutEvent event) {
        faces.getExternalContext().invalidateSession();
    }

    public void redirectToLoginOnLogout(@Observes PostLoggedOutEvent event) {
        view.navigateTo(Login.class);
    }

    @PreRenderView
    public void redirectHomeIfLoggedIn() {
        if (identity.isLoggedIn()) {
            view.navigateTo(SecuredPages.Home.class);
        }
    }

}
