package org.jboss.examples.deltaspike.expensetracker.app.security.view;

import static java.lang.annotation.ElementType.*;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.*;
import java.lang.annotation.Target;
import java.util.Collections;
import java.util.Set;
import javax.enterprise.inject.Stereotype;
import javax.inject.Inject;
import org.apache.deltaspike.core.api.config.view.metadata.ViewMetaData;
import org.apache.deltaspike.security.api.authorization.AccessDecisionVoter;
import org.apache.deltaspike.security.api.authorization.AccessDecisionVoterContext;
import org.apache.deltaspike.security.api.authorization.Secured;
import org.apache.deltaspike.security.api.authorization.SecurityViolation;
import org.picketlink.Identity;

@Target({TYPE, METHOD, FIELD})
@Retention(RUNTIME)
@Inherited
@Stereotype
@ViewMetaData // since 1.0.1
@Secured(LoggedIn.Voter.class)
public @interface LoggedIn {

    public static class Voter implements AccessDecisionVoter {

        @Inject
        private Identity identity;

        @Override
        public Set<SecurityViolation> checkPermission(AccessDecisionVoterContext accessDecisionVoterContext) {
            if (!identity.isLoggedIn()) {
                return Collections.<SecurityViolation>singleton(new SecurityViolation() {
                    @Override
                    public String getReason() {
                        return "No user is logged in.";
                    }
                });
            } else {
                return Collections.<SecurityViolation>emptySet();
            }
        }

    }

}
