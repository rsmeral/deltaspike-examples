package org.jboss.examples.deltaspike.task.management.security;

import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.apache.deltaspike.core.api.config.view.navigation.ViewNavigationHandler;
import org.apache.deltaspike.security.api.authorization.AccessDecisionVoter;
import org.apache.deltaspike.security.api.authorization.AccessDecisionVoterContext;
import org.apache.deltaspike.security.api.authorization.SecurityViolation;
import org.jboss.examples.deltaspike.task.management.domain.User;
import org.jboss.examples.deltaspike.task.management.pages.Pages;
import org.jboss.examples.deltaspike.task.management.util.Utils;

public class AdminDecisionVoter implements AccessDecisionVoter {

    @Inject
    private User user;

    @Inject
    private LoginController loginController;

    @Inject
    private Utils utils;

    @Inject
    private ViewNavigationHandler viewNavigationHandler;

    @Inject
    private FacesContext faces;

    @Inject
    private LoggedInDecisionVoter loggedInVoter;

    public Set<SecurityViolation> checkPermission(AccessDecisionVoterContext accessDecisionVoterContext) {

        loggedInVoter.checkPermission(accessDecisionVoterContext);

        if (!user.isAdministator()) {

            faces.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Access denied !!!", null));
            faces.getExternalContext().getFlash().setKeepMessages(true);
            viewNavigationHandler.navigateTo(Pages.AccessDenied.class);

        }

        return null;
    }

}
