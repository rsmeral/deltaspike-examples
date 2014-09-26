package org.jboss.examples.deltaspike.expensetracker.domain.logic;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import org.apache.deltaspike.security.api.authorization.SecurityBindingType;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Represents an operation which can be performed on an expense report.
 */
@SecurityBindingType
@Retention(RUNTIME)
@Target({TYPE, METHOD})
public @interface Operation {

    public enum Type {

        OPEN, ASSIGN, UNASSIGN, SUBMIT, REJECT, APPROVE, SETTLE
    }

    public Type value();

}
