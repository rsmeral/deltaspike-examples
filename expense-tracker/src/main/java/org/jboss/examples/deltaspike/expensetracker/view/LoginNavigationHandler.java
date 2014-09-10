package org.jboss.examples.deltaspike.expensetracker.view;

import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.apache.deltaspike.cdise.api.ContextControl;
import org.apache.deltaspike.core.api.config.view.ViewRef;
import org.apache.deltaspike.core.api.config.view.controller.InitView;
import org.apache.deltaspike.core.api.config.view.controller.PreRenderView;
import org.apache.deltaspike.core.api.config.view.navigation.ViewNavigationHandler;
import org.apache.deltaspike.core.api.scope.WindowScoped;
import org.apache.deltaspike.core.spi.scope.window.WindowContext;
import org.apache.deltaspike.jsf.api.message.JsfMessage;
import org.jboss.examples.deltaspike.expensetracker.app.DemoInitializer;
import org.jboss.examples.deltaspike.expensetracker.app.resources.AppMessages;
import org.picketlink.Identity;
import org.picketlink.authentication.event.AlreadyLoggedInEvent;
import org.picketlink.authentication.event.LoggedInEvent;
import org.picketlink.authentication.event.LoginFailedEvent;
import org.picketlink.authentication.event.PostLoggedOutEvent;
import org.picketlink.authentication.event.PreLoggedOutEvent;

/**
 * Handles some automatic navigation by observing authentication events and
 * Login view's lifecycle phases using {@link ViewRef} and
 * {@link PreRenderView}.
 */
@ViewRef(config = Login.class)
@RequestScoped
public class LoginNavigationHandler implements Serializable {

    @Inject
    private ViewNavigationHandler view;

    @Inject
    private FacesContext faces;

    @Inject
    private JsfMessage<AppMessages> msg;

    @Inject
    private Identity identity;

    @Inject
    private DemoInitializer init;
    
    @Inject private WindowContext wctx;

    /*
     * Navigates to Home on regular log in, or to the last view that was
     * requested and resulted in Access Denied exception.
     */
    public void redirectOnLogin(@Observes LoggedInEvent event, AccessDeniedViewHolder viewHolder) {
        if (viewHolder.hasDeniedView()) {
            view.navigateTo(viewHolder.getDeniedView());
            viewHolder.resetDeniedView();
        } else {
            view.navigateTo(SecuredPages.Home.class);
        }
    }

    public void redirectHomeWhenAlreadyLoggedIn(@Observes AlreadyLoggedInEvent event) {
        view.navigateTo(SecuredPages.Home.class);
    }

    public void redirectBackOnFailedLogin(@Observes LoginFailedEvent event) {
        msg.addError().loginFailed();
        view.navigateTo(Login.class);
    }

    public void invalidateSessionOnLogout(@Observes PreLoggedOutEvent event) {
//        faces.getExternalContext().invalidateSession();
        wctx.closeWindow(wctx.getCurrentWindowId());
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
    
    @InitView
    public void init() {
        init.initialize();
    }

}
