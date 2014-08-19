package org.jboss.examples.deltaspike.expensetracker.app.resources;

import java.io.Serializable;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.deltaspike.core.api.scope.WindowScoped;
import org.apache.deltaspike.data.api.audit.CurrentUser;
import org.jboss.examples.deltaspike.expensetracker.service.EmployeeService;
import org.jboss.examples.deltaspike.expensetracker.data.EmployeeRepository;
import org.jboss.examples.deltaspike.expensetracker.domain.model.Employee;
import org.picketlink.Identity;
import org.picketlink.idm.model.basic.User;

@WindowScoped
public class CurrentEmployeeProducer implements Serializable {

    private Employee cached;

    @Inject
    private Identity identity;

    @Inject
    private EmployeeRepository repo;

    @Produces
    @WindowScoped
    @CurrentUser
    @Named
    public Employee getCurrentEmployee() {
        if (cached == null) {
            Long employeeId = identity.getAccount().<Long>getAttribute(EmployeeService.EMPLOYEE_ID_ATTRIBUTE).getValue();
            Employee found = repo.findBy(employeeId);
            cached = found;
        }

        return cached;
    }
    
    @Produces
    @WindowScoped
    @Named
    @CurrentUser
    public User getCurrentUser() {
        return ((User)identity.getAccount());
    }

}
