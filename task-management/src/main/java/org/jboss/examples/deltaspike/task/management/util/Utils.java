package org.jboss.examples.deltaspike.task.management.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;

import org.apache.deltaspike.security.api.authorization.SecurityViolation;

@Model
public class Utils {

    public Set<SecurityViolation> createSecurityViolations(String... violations) {
        HashSet<SecurityViolation> secViolations = new HashSet<SecurityViolation>();

        for (final String sv : violations) {
            SecurityViolation secViolation = new SecurityViolation() {

                public String getReason() {
                    return sv;
                }
            };
            secViolations.add(secViolation);
        }

        return secViolations;
    }

    public String formatShortDate(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat formater = new SimpleDateFormat("dd-MM-yyyy");
        return formater.format(date);
    }

    public String formatLongDate(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat formater = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss:SSS");
        return formater.format(date);
    }

    @Produces
    @RequestScoped
    public FacesContext getFacesContext() {
        return FacesContext.getCurrentInstance();
    }

}
