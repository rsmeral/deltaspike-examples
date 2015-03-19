package org.jboss.examples.deltaspike.servletobjects.servlet;

import static java.lang.annotation.ElementType.*;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.enterprise.util.Nonbinding;
import javax.inject.Inject;
import javax.inject.Qualifier;
import javax.servlet.http.HttpServletRequest;
import org.apache.deltaspike.core.api.common.DeltaSpike;

@Target({FIELD, TYPE, METHOD, PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Qualifier
public @interface RequestParam {

    @Nonbinding
    public String value() default "";

    public static class Producer {

        @Inject
        @DeltaSpike
        private HttpServletRequest req;

        @Produces
        @RequestParam
        public String produce(InjectionPoint ip) {
            RequestParam requestParam = ip.getAnnotated().getAnnotation(RequestParam.class);
            String paramName = requestParam.value().equals("") ? ip.getMember().getName() : requestParam.value();
            return req.getParameter(paramName);
        }

    }
}
