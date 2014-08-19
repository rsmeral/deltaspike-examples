package org.jboss.examples.deltaspike.expensetracker.domain.model;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;

/**
 * A compromise between type safety and enum-like behaviour.
 * Constants are produced and @Named to enable type-safety in EL as well.
 */
public class EmployeeRole {

    @Produces
    @ApplicationScoped
    @Named
    public static final String ADMIN = "ADMIN";

    @Produces
    @ApplicationScoped
    @Named
    public static final String EMPLOYEE = "EMPLOYEE";

    @Produces
    @ApplicationScoped
    @Named
    public static final String ACCOUNTANT = "ACCOUNTANT";

    /**
     * A hack to replace the valueOf method of enums
     */
    public static List<String> getAllRoles() {
        List<String> result = new ArrayList<String>();
        for (Field field : EmployeeRole.class.getDeclaredFields()) {
            try {
                result.add((String) field.get(null));
            } catch (Exception ex) {
            }
        }
        return result;
    }

}
