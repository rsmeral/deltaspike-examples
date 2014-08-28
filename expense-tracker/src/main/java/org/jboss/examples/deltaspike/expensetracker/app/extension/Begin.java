package org.jboss.examples.deltaspike.expensetracker.app.extension;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for the {@link ConversationInterceptor} which signifies that
 * before the annotated method gets invoked, a long-running conversation should
 * be started.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Begin {

    /**
     * Indicates whether a new conversation should be started even when there
     * already is an active long-running conversation.
     */
    boolean force() default false;

}
