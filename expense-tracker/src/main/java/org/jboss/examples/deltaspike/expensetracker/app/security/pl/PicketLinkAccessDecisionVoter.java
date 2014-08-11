package org.jboss.examples.deltaspike.expensetracker.app.security.pl;

import java.util.Collections;
import java.util.Set;
import org.apache.deltaspike.security.api.authorization.AccessDecisionVoter;
import org.apache.deltaspike.security.api.authorization.AccessDecisionVoterContext;
import org.apache.deltaspike.security.api.authorization.SecurityViolation;

public class PicketLinkAccessDecisionVoter implements AccessDecisionVoter {


    @Override
    public Set<SecurityViolation> checkPermission(AccessDecisionVoterContext advc) {
//        advc.getSource();
        System.err.println("In checkPermission");
        System.err.println(advc.getSource().getClass());
        return Collections.EMPTY_SET;
    }

}
