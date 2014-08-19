package org.jboss.examples.deltaspike.expensetracker.app;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import org.jboss.examples.deltaspike.expensetracker.service.EmployeeService;
import org.jboss.examples.deltaspike.expensetracker.domain.model.EmployeeRole;
import static org.jboss.examples.deltaspike.expensetracker.domain.model.EmployeeRole.*;
import org.jboss.examples.deltaspike.expensetracker.data.EmployeeRepository;
import org.jboss.examples.deltaspike.expensetracker.domain.model.Employee;
import org.picketlink.idm.IdentityManager;
import org.picketlink.idm.model.basic.Role;

@Startup
@Singleton
@TransactionManagement(TransactionManagementType.BEAN)
public class DemoInitializer {

    @Inject
    private EmployeeService empSvc;

    @Inject
    private EmployeeRepository empRepo;

    @Inject
    private IdentityManager idm;

    @PostConstruct
    public void initialize() {
        
        for (String role : EmployeeRole.getAllRoles()) {
            idm.add(new Role(role));
        }
        
        empSvc.registerEmployee(empRepo.save(new Employee("Admin", "Administrator", "admin@example.com", "123456789")), "admin", "admin", EMPLOYEE, ACCOUNTANT, ADMIN);
        empSvc.registerEmployee(empRepo.save(new Employee("John", "Employee", "john@example.com", "987654321")), "john", "john", EMPLOYEE);
        empSvc.registerEmployee(empRepo.save(new Employee("Anna", "Accountant", "anna@example.com", "654321987")), "anna", "anna", EMPLOYEE, ACCOUNTANT);

    }

}
