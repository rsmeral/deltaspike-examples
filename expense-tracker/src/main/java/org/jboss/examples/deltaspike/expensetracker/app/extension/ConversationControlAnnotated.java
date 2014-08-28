package org.jboss.examples.deltaspike.expensetracker.app.extension;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.interceptor.InterceptorBinding;

/**
 * An interceptor binding for the {@link ConversationInterceptor}. Can be used
 * e.g. on view controllers, where individual methods can be annotated
 * {@link Begin} and {@link End}.
 */
@Inherited
@InterceptorBinding
@Retention(RUNTIME)
@Target({METHOD, TYPE})
public @interface ConversationControlAnnotated {
}
