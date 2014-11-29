package org.jboss.examples.deltaspike.servletobjects.servlet;

import static java.lang.annotation.ElementType.*;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Qualifier;
import javax.servlet.ServletContext;
import org.apache.deltaspike.core.api.common.DeltaSpike;

@Target({FIELD, TYPE, METHOD, PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Qualifier
public @interface ContextPath {

    public static class Producer {

        @Inject
        @DeltaSpike
        private ServletContext ctx;

        @Produces
        @ContextPath
        public String contextPath() {
            return ctx.getContextPath();
        }
    }
}
