package org.jboss.examples.deltaspike.expensetracker.app.resources;

import java.io.Serializable;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.deltaspike.core.api.scope.WindowScoped;
import org.apache.deltaspike.data.api.audit.CurrentUser;
import org.jboss.examples.deltaspike.expensetracker.data.EmployeeRepository;
import org.jboss.examples.deltaspike.expensetracker.domain.model.Employee;
import org.jboss.examples.deltaspike.expensetracker.service.EmployeeService;
import org.picketlink.Identity;
import org.picketlink.authentication.event.LoggedInEvent;
import org.picketlink.idm.model.basic.User;

/**
 * Produces the current Employee for use with the Data module's audit
 * capability.
 *
 * The
 * {@link org.jboss.examples.deltaspike.expensetracker.domain.model.ExpenseReport}
 * entity has a {@code Employee lastChangedBy} field annotated
 * {@link org.apache.deltaspike.data.api.audit.ModifiedBy}. Upon persisting an
 * ExpenseReport, the Data module looks for a producer of {@code Employee} qualified
 * {@code @CurrentUser}.
 *
 */
@WindowScoped
public class CurrentEmployee implements Serializable {

    /*
     * Dummy used as event payload.
     */
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
        if (cached != null) {
            cached = repo.merge(cached);
        }
        return cached;
    }

    @Produces
    @WindowScoped
    @Named
    @CurrentUser
    public User getCurrentUser(Identity identity) {
        return ((User) identity.getAccount());
    }

}
