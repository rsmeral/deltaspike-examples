package org.jboss.examples.deltaspike.expensetracker.app.resources;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.deltaspike.data.api.audit.CurrentUser;
import org.jboss.examples.deltaspike.expensetracker.app.security.EmployeeRegistration;
import org.jboss.examples.deltaspike.expensetracker.data.EmployeeRepository;
import org.jboss.examples.deltaspike.expensetracker.model.Employee;
import org.picketlink.Identity;
import org.picketlink.idm.model.basic.User;

@SessionScoped
public class CurrentEmployeeProducer implements Serializable {

    private Employee cached;

    @Inject
    private Identity identity;

    @Inject
    private EmployeeRepository repo;

    @Produces
    @CurrentUser
    @Named
    public Employee getCurrentEmployee() {
        if (cached == null) {
            Long employeeId = identity.getAccount().<Long>getAttribute(EmployeeRegistration.EMPLOYEE_ID_ATTRIBUTE).getValue();
            Employee found = repo.findBy(employeeId);
            cached = found;
        }

        return cached;
    }
    
    @Produces
    @Named
    @CurrentUser
    public User getCurrentUser() {
        return ((User)identity.getAccount());
    }

}
