package org.jboss.examples.deltaspike.expensetracker.app;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import org.jboss.examples.deltaspike.expensetracker.app.security.EmployeeRegistration;
import org.jboss.examples.deltaspike.expensetracker.app.security.EmployeeRole;
import org.jboss.examples.deltaspike.expensetracker.data.EmployeeRepository;
import org.jboss.examples.deltaspike.expensetracker.model.Employee;
import org.picketlink.idm.IdentityManager;
import static org.jboss.examples.deltaspike.expensetracker.app.security.EmployeeRole.*;
import org.picketlink.idm.model.basic.Role;

@Startup
@Singleton
@TransactionManagement(TransactionManagementType.BEAN)
public class DemoInitializer {

    @Inject
    private EmployeeRegistration empSvc;

    @Inject
    private EmployeeRepository empRepo;

    @Inject
    private IdentityManager idm;

    @PostConstruct
    public void initialize() {

        for (String role : EmployeeRole.getAllRoles()) {
            idm.add(new Role(role));
        }

        empSvc.registerEmployee(empRepo.save(new Employee("Admin", "Administrator", "admin@example.com", "123456789")), "admin", "admin", EMPLOYEE_ROLE, ACCOUNTANT_ROLE, ADMIN_ROLE);
        empSvc.registerEmployee(empRepo.save(new Employee("John", "Employee", "john@example.com", "987654321")), "john", "john", EMPLOYEE_ROLE);
        empSvc.registerEmployee(empRepo.save(new Employee("Anna", "Accountant", "anna@example.com", "654321987")), "anna", "anna", EMPLOYEE_ROLE, ACCOUNTANT_ROLE);

    }

}
