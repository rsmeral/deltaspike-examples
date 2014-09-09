package org.jboss.examples.deltaspike.task.management.security;

import java.util.Set;

import javax.inject.Inject;

import org.apache.deltaspike.security.api.authorization.AccessDecisionVoter;
import org.apache.deltaspike.security.api.authorization.AccessDecisionVoterContext;
import org.apache.deltaspike.security.api.authorization.SecurityViolation;
import org.jboss.examples.deltaspike.task.management.util.Utils;

public class LoggedInDecisionVoter implements AccessDecisionVoter {

    @Inject
    private LoginController loginController;

    @Inject
    private Utils utils;

    public Set<SecurityViolation> checkPermission(AccessDecisionVoterContext accessDecisionVoterContext) {


        if (!loginController.isUserLoggedIn()){
            return utils.createSecurityViolations("You are not logged in");
        }
        return null;
    }

}
