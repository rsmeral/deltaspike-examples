package org.jboss.examples.deltaspike.task.management.security;

import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.apache.deltaspike.core.api.config.view.navigation.ViewNavigationHandler;
import org.apache.deltaspike.security.api.authorization.AccessDecisionVoter;
import org.apache.deltaspike.security.api.authorization.AccessDecisionVoterContext;
import org.apache.deltaspike.security.api.authorization.SecurityViolation;
import org.jboss.examples.deltaspike.task.management.pages.Pages;
import org.jboss.examples.deltaspike.task.management.util.Utils;

public class LoggedInDecisionVoter implements AccessDecisionVoter {

    @Inject
    private LoginController loginController;

    @Inject
    private Utils utils;

    @Inject
    private ViewNavigationHandler viewNavigationHandler;

    @Inject
    private FacesContext faces;

    public Set<SecurityViolation> checkPermission(AccessDecisionVoterContext accessDecisionVoterContext) {

        if (!loginController.isUserLoggedIn()) {

            faces.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "You have to login !!!", null));
            faces.getExternalContext().getFlash().setKeepMessages(true);

            viewNavigationHandler.navigateTo(Pages.Login.class);
        }

        return null;
    }
}
