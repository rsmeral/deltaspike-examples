package org.jboss.examples.deltaspike.expensetracker.app.extension;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.enterprise.inject.Stereotype;
import javax.inject.Named;
import org.apache.deltaspike.core.api.scope.GroupedConversationScoped;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Stereotype

@Named
@GroupedConversationScoped
@ConversationControlAnnotated
public @interface Controller {
}
