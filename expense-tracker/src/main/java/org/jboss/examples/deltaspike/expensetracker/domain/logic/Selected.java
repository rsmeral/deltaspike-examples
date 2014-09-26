package org.jboss.examples.deltaspike.expensetracker.domain.logic;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import org.apache.deltaspike.security.api.authorization.SecurityParameterBinding;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 *
 */
@Retention(RUNTIME)
@Target(PARAMETER)
@SecurityParameterBinding
public @interface Selected {

}
