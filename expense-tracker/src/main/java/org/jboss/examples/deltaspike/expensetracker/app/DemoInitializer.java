package org.jboss.examples.deltaspike.expensetracker.app;

import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import org.apache.deltaspike.cdise.api.ContextControl;
import org.apache.deltaspike.core.api.lifecycle.Initialized;
import org.jboss.examples.deltaspike.expensetracker.data.EmployeeRepository;
import org.jboss.examples.deltaspike.expensetracker.data.PurposeRepository;
import org.jboss.examples.deltaspike.expensetracker.domain.model.Employee;
import org.jboss.examples.deltaspike.expensetracker.domain.model.EmployeeRole;
import static org.jboss.examples.deltaspike.expensetracker.domain.model.EmployeeRole.*;
import org.jboss.examples.deltaspike.expensetracker.domain.model.Purpose;
import org.jboss.examples.deltaspike.expensetracker.service.EmployeeService;
import org.picketlink.idm.IdentityManager;
import org.picketlink.idm.model.basic.Role;

public class DemoInitializer {

    @Inject
    private EmployeeService empSvc;

    @Inject
    private EmployeeRepository empRepo;

    @Inject
    private PurposeRepository purposeRepo;

    @Inject
    private IdentityManager idm;

    @Inject
    private ContextControl ctxCtl;

    /*
     * DeltaSpike Servlet module fires an event when the ServletContext is
     * initialized, which happens at application deployment time and only once.
     * This cane be used to initialize an application, similarly to using
     * @Startup/@Singleton/@PostConstruct.
     */
    public void initialize(@Observes @Initialized ServletContext ctx) {
        /*
         * At this time, only the application scope is available. We need to
         * start additional scopes which are used by beans we intend to use, 
         * like the conversation-scoped EntityManager or the request-scoped
         * IdentityManager.
         */
        ctxCtl.startContext(SessionScoped.class);
        ctxCtl.startContext(RequestScoped.class);
        ctxCtl.startContext(ConversationScoped.class);

        for (String role : EmployeeRole.getAllRoles()) {
            idm.add(new Role(role));
        }

        empSvc.registerEmployee(empRepo.save(new Employee("Admin", "Administrator", "admin@example.com", "123456789")), "admin", "admin", EMPLOYEE, ACCOUNTANT, ADMIN);
        empSvc.registerEmployee(empRepo.save(new Employee("John", "Employee", "john@example.com", "987654321")), "john", "john", EMPLOYEE);
        empSvc.registerEmployee(empRepo.save(new Employee("Anna", "Accountant", "anna@example.com", "654321987")), "anna", "anna", EMPLOYEE, ACCOUNTANT);

        purposeRepo.save(new Purpose("Travel", "Travel expenses (train, plane, public transport, taxi)"));

        ctxCtl.stopContext(ConversationScoped.class);
        ctxCtl.stopContext(RequestScoped.class);
        ctxCtl.stopContext(SessionScoped.class);
    }

}
