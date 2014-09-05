package org.jboss.examples.deltaspike.expensetracker.app;

import java.math.BigDecimal;
import java.util.GregorianCalendar;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import org.apache.deltaspike.cdise.api.ContextControl;
import org.apache.deltaspike.core.api.lifecycle.Initialized;
import org.jboss.examples.deltaspike.expensetracker.data.EmployeeRepository;
import org.jboss.examples.deltaspike.expensetracker.data.ExpenseReportRepository;
import org.jboss.examples.deltaspike.expensetracker.data.ExpenseRepository;
import org.jboss.examples.deltaspike.expensetracker.data.PurposeRepository;
import org.jboss.examples.deltaspike.expensetracker.data.ReimbursementRepository;
import org.jboss.examples.deltaspike.expensetracker.domain.model.Employee;
import org.jboss.examples.deltaspike.expensetracker.domain.model.EmployeeRole;
import static org.jboss.examples.deltaspike.expensetracker.domain.model.EmployeeRole.*;
import org.jboss.examples.deltaspike.expensetracker.domain.model.Expense;
import org.jboss.examples.deltaspike.expensetracker.domain.model.ExpenseReport;
import org.jboss.examples.deltaspike.expensetracker.domain.model.Purpose;
import org.jboss.examples.deltaspike.expensetracker.domain.model.Reimbursement;
import org.jboss.examples.deltaspike.expensetracker.domain.model.ReportStatus;
import org.jboss.examples.deltaspike.expensetracker.service.EmployeeService;
import org.picketlink.idm.IdentityManager;
import org.picketlink.idm.model.basic.Role;

/**
 * Supplies initial data. Used instead of import.sql to support portability.
 */
public class DemoInitializer {

    @Inject
    private EmployeeService empSvc;

    @Inject
    private EmployeeRepository empRepo;

    @Inject
    private PurposeRepository purposeRepo;

    @Inject
    private ExpenseRepository expenseRepo;

    @Inject
    private ReimbursementRepository reimbursementRepo;

    @Inject
    private IdentityManager idm;

    @Inject
    private ExpenseReportRepository expenseReportRepo;

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

        final Employee admin = new Employee("Admin", "Administrator", "admin@example.com", "123456789");
        final Employee john = new Employee("John", "Employee", "john@example.com", "987654321");
        final Employee anna = new Employee("Anna", "Accountant", "anna@example.com", "654321987");

        empSvc.registerEmployee(empRepo.saveAndFlush(admin), "admin", "admin", EMPLOYEE, ACCOUNTANT, ADMIN);
        empSvc.registerEmployee(empRepo.saveAndFlush(john), "john", "john", EMPLOYEE);
        empSvc.registerEmployee(empRepo.saveAndFlush(anna), "anna", "anna", EMPLOYEE, ACCOUNTANT);

        final Purpose travel = purposeRepo.save(new Purpose("Travel", "Train, plane, public transport, taxi"));
        final Purpose accomodation = purposeRepo.save(new Purpose("Accommodation", "Hotels"));
        final Purpose food = purposeRepo.save(new Purpose("Food/Drinks", "Any meals"));
        final Purpose fuel = purposeRepo.save(new Purpose("Fuel", "Gas, LPG, Electricity"));

        final ExpenseReport report = new ExpenseReport("GeeCon 2013", "Krakow, 3 days", john, anna, ReportStatus.SUBMITTED);
        
        expenseReportRepo.save(report);

        expenseRepo.save(new Expense(food, null, new GregorianCalendar(2013, 4, 14).getTime(), BigDecimal.valueOf(25), report));
        expenseRepo.save(new Expense(food, null, new GregorianCalendar(2013, 4, 15).getTime(), BigDecimal.valueOf(20), report));
        expenseRepo.save(new Expense(food, null, new GregorianCalendar(2013, 4, 16).getTime(), BigDecimal.valueOf(20), report));
        expenseRepo.save(new Expense(accomodation, null, new GregorianCalendar(2013, 4, 16).getTime(), BigDecimal.valueOf(100), report));
        expenseRepo.save(new Expense(travel, null, new GregorianCalendar(2013, 4, 14).getTime(), BigDecimal.valueOf(45), report));
        expenseRepo.save(new Expense(travel, null, new GregorianCalendar(2013, 4, 16).getTime(), BigDecimal.valueOf(50), report));

        reimbursementRepo.save(new Reimbursement(anna, new GregorianCalendar(2013, 4, 13).getTime(), BigDecimal.valueOf(150), report));

        ctxCtl.stopContext(ConversationScoped.class);
        ctxCtl.stopContext(RequestScoped.class);
        ctxCtl.stopContext(SessionScoped.class);
    }

}
