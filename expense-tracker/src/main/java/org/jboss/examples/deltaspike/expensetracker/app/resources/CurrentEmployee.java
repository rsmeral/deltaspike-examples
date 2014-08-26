package org.jboss.examples.deltaspike.expensetracker.app.resources;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.deltaspike.data.api.audit.CurrentUser;
import org.jboss.examples.deltaspike.expensetracker.data.EmployeeRepository;
import org.jboss.examples.deltaspike.expensetracker.domain.model.Employee;
import org.jboss.examples.deltaspike.expensetracker.service.EmployeeService;
import org.picketlink.Identity;
import org.picketlink.authentication.event.LoggedInEvent;
import org.picketlink.idm.model.basic.User;

@SessionScoped
public class CurrentEmployee implements Serializable {

    public static class Modified {
    }

    private Employee cached;

    @Inject
    private EmployeeRepository repo;

    private void updateCachedEmployee(Identity identity) {
        cached = repo.findBy(identity.getAccount().<Long>getAttribute(EmployeeService.EMPLOYEE_ID_ATTRIBUTE).getValue());
    }

    public void setOnLogin(@Observes LoggedInEvent event, Identity identity) {
        updateCachedEmployee(identity);
    }
    
    public void setOnModification(@Observes Modified event, Identity identity) {
        updateCachedEmployee(identity);
    }

    // TODO: This should be cacheable (wider scope)
    @Produces
    @Named
    @CurrentUser
    public Employee getCurrentEmployee() {
        return repo.merge(cached);
    }

    @Produces
    @SessionScoped
    @Named
    @CurrentUser
    public User getCurrentUser(Identity identity) {
        return ((User) identity.getAccount());
    }

}