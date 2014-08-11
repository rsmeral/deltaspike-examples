package org.jboss.examples.deltaspike.expensetracker.app.security.view;

import java.util.Collections;
import java.util.Set;
import javax.inject.Inject;
import org.apache.deltaspike.security.api.authorization.AccessDecisionVoter;
import org.apache.deltaspike.security.api.authorization.AccessDecisionVoterContext;
import org.apache.deltaspike.security.api.authorization.SecurityViolation;
import org.picketlink.Identity;

public class LoginAccessDecisionVoter implements AccessDecisionVoter {

    @Inject
    private Identity identity;

    @Override
    public Set<SecurityViolation> checkPermission(AccessDecisionVoterContext advc) {
        return identity.isLoggedIn() ? Collections.EMPTY_SET : Collections.singleton(new SecurityViolation() {

            @Override
            public String getReason() {
                return "No user is logged in.";
            }
        });
    }

}
