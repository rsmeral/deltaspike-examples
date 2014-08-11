package org.jboss.examples.deltaspike.expensetracker.app.security;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.inject.Typed;
import javax.inject.Named;

/**
 * A compromise between type safety and enum-like behaviour.
 * Constants are @Named to enable type-safety in EL as well.
 */
public class EmployeeRole {

    @Named
    public static final String ADMIN_ROLE = "administrator";
    @Named
    public static final String EMPLOYEE_ROLE = "employee";
    @Named
    public static final String ACCOUNTANT_ROLE = "accountant";

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
