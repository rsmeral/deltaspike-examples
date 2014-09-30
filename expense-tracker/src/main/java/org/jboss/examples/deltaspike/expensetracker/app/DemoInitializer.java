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
import org.jboss.examples.deltaspike.expensetracker.data.*;
import org.jboss.examples.deltaspike.expensetracker.domain.model.*;

import static org.jboss.examples.deltaspike.expensetracker.domain.model.EmployeeRole.*;

/**
 * Supplies initial data. Used instead of import.sql to support portability.
 */
public class DemoInitializer {

    @Inject
    private EmployeeRepository empRepo;

    @Inject
    private PurposeRepository purposeRepo;

    @Inject
    private ExpenseRepository expenseRepo;

    @Inject
    private ReimbursementRepository reimbursementRepo;

    @Inject
    private ExpenseReportRepository expenseReportRepo;

    @Inject
    private ContextControl ctxCtl;

    @Inject
    private IDMInitializer idmInit;

    /*
     * DeltaSpike Servlet module fires an event when the ServletContext is
     * initialized, which happens at application deployment time and only once.
     * This can be used to initialize an application, similarly to using
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

        Employee admin = empRepo.save(new Employee("Admin", "Administrator", "admin@example.com", "123456789"));
        Employee john = empRepo.save(new Employee("John", "Employee", "john@example.com", "987654321"));
        Employee anna = empRepo.save(new Employee("Anna", "Accountant", "anna@example.com", "654321987"));

        idmInit.initializeRoles();

        idmInit.registerEmployee(admin, "admin", "admin", EMPLOYEE, ACCOUNTANT, ADMIN);
        idmInit.registerEmployee(john, "john", "john", EMPLOYEE);
        idmInit.registerEmployee(anna, "anna", "anna", EMPLOYEE, ACCOUNTANT);

        // add data
        Purpose travel = purposeRepo.save(new Purpose("Travel", "Train, plane, public transport, taxi"));
        Purpose accomodation = purposeRepo.save(new Purpose("Accommodation", "Hotels"));
        Purpose food = purposeRepo.save(new Purpose("Food/Drinks", "Any meals"));
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
