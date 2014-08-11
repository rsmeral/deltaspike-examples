package org.jboss.examples.deltaspike.expensetracker.app.security.pl;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.apache.deltaspike.core.api.config.view.metadata.ViewMetaData;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@ViewMetaData()
public @interface RolesAllowed {

    String[] value();
    
}
