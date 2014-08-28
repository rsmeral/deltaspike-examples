package org.jboss.examples.deltaspike.expensetracker.view;

import java.io.Serializable;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.apache.deltaspike.core.api.config.view.ViewRef;
import org.apache.deltaspike.core.api.config.view.controller.PreRenderView;
import org.apache.deltaspike.core.api.config.view.navigation.ViewNavigationHandler;
import org.apache.deltaspike.jsf.api.message.JsfMessage;
import org.jboss.examples.deltaspike.expensetracker.app.resources.AppMessages;
import org.picketlink.Identity;
import org.picketlink.authentication.event.AlreadyLoggedInEvent;
import org.picketlink.authentication.event.LoggedInEvent;
import org.picketlink.authentication.event.LoginFailedEvent;
import org.picketlink.authentication.event.PostLoggedOutEvent;

/**
 * Handles some automatic navigation by observing authentication events
 * and Login view's lifecycle phases using {@link ViewRef} and
 * {@link PreRenderView}.
 */
@ViewRef(config = Login.class)
@ApplicationScoped
public class LoginNavigationHandler implements Serializable {

    @Inject
    private ViewNavigationHandler view;

    @Inject
    private FacesContext faces;

    @Inject
    private JsfMessage<AppMessages> msg;

    @Inject
    private Identity identity;

    public void redirectHomeOnLogin(@Observes LoggedInEvent event) {
        view.navigateTo(SecuredPages.Home.class);
    }

    public void redirectHomeWhenAlreadyLoggedIn(@Observes AlreadyLoggedInEvent event) {
        view.navigateTo(SecuredPages.Home.class);
    }

    public void redirectBackOnFailedLogin(@Observes LoginFailedEvent event) {
        msg.addError().loginFailed();
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
