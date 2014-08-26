package org.jboss.examples.deltaspike.expensetracker.app;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.jboss.examples.deltaspike.expensetracker.data.EmployeeRepository;
import org.jboss.examples.deltaspike.expensetracker.data.PurposeRepository;
import org.jboss.examples.deltaspike.expensetracker.domain.model.Employee;
import org.jboss.examples.deltaspike.expensetracker.domain.model.EmployeeRole;
import static org.jboss.examples.deltaspike.expensetracker.domain.model.EmployeeRole.*;
import org.jboss.examples.deltaspike.expensetracker.domain.model.Purpose;
import org.jboss.examples.deltaspike.expensetracker.service.EmployeeService;
import org.picketlink.idm.IdentityManager;
import org.picketlink.idm.model.basic.Role;

@ApplicationScoped
@Named
public class DemoInitializer {

    @Inject
    private EmployeeService empSvc;

    @Inject
    private EmployeeRepository empRepo;
    
    @Inject
    private PurposeRepository purposeRepo;

    @Inject
    private IdentityManager idm;

    private boolean initialized = false;

    public boolean isInitialized() {
        return initialized;
    }

    public void setInitialized(boolean initialized) {
        this.initialized = initialized;
    }

    public void initialize() {
        for (String role : EmployeeRole.getAllRoles()) {
            idm.add(new Role(role));
        }

        empSvc.registerEmployee(empRepo.save(new Employee("Admin", "Administrator", "admin@example.com", "123456789")), "admin", "admin", EMPLOYEE, ACCOUNTANT, ADMIN);
        empSvc.registerEmployee(empRepo.save(new Employee("John", "Employee", "john@example.com", "987654321")), "john", "john", EMPLOYEE);
        empSvc.registerEmployee(empRepo.save(new Employee("Anna", "Accountant", "anna@example.com", "654321987")), "anna", "anna", EMPLOYEE, ACCOUNTANT);

        purposeRepo.save(new Purpose("Travel", "Travel expenses (train, plane, public transport, taxi)"));
        
        initialized = true;
    }

}
