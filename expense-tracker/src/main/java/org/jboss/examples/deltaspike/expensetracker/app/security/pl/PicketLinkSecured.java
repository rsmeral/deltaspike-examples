package org.jboss.examples.deltaspike.expensetracker.app.security.pl;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.enterprise.inject.Stereotype;
import org.apache.deltaspike.security.api.authorization.Secured;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Stereotype
@Secured(PicketLinkAccessDecisionVoter.class)
public @interface PicketLinkSecured {
    
}
