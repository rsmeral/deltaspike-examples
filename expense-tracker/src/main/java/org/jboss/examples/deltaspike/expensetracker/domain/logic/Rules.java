package org.jboss.examples.deltaspike.expensetracker.domain.logic;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import org.apache.deltaspike.data.api.audit.CurrentUser;
import org.jboss.examples.deltaspike.expensetracker.app.security.Authorizations;
import org.jboss.examples.deltaspike.expensetracker.domain.model.Employee;
import static org.jboss.examples.deltaspike.expensetracker.domain.model.EmployeeRole.ACCOUNTANT;
import static org.jboss.examples.deltaspike.expensetracker.domain.model.EmployeeRole.EMPLOYEE;
import org.jboss.examples.deltaspike.expensetracker.domain.model.ExpenseReport;
import static org.jboss.examples.deltaspike.expensetracker.domain.model.ReportStatus.*;
import org.picketlink.authentication.event.LoggedInEvent;

/**
 * Contains state-transfer conditions and authorization rules for
 * ExpenseReports.
 */
@SessionScoped
public class Rules implements Serializable {

    private Employee currentEmployee;

    public Rules() {
    }

    @Inject
    public Rules(@CurrentUser Employee employee) {
        this.currentEmployee = employee;
    }

    public void setCurrentEmployee(@Observes LoggedInEvent event, @CurrentUser Employee employee) {
        this.currentEmployee = employee;
    }

    @Inject
    private Authorizations auth;

    public static boolean isAssignee(ExpenseReport report, Employee employee) {
        return employee.equals(report.getAssignee());
    }

    public static boolean isReporter(ExpenseReport report, Employee employee) {
        return employee.equals(report.getReporter());
    }

    /*
     * STATE-BASED RULES
     */
    public static boolean canBeOpened(ExpenseReport report) {
        return report.getStatus() == null || report.getStatus() == REJECTED;
    }

    public static boolean canBeAssigned(ExpenseReport report) {
        return report.getStatus() == SUBMITTED && report.getAssignee() == null;
    }

    public static boolean canBeUnassigned(ExpenseReport report) {
        return report.getStatus() == SUBMITTED && report.getAssignee() != null;
    }

    public static boolean canBeSubmitted(ExpenseReport report) {
        return report.getStatus() == OPEN;
    }

    public static boolean canBeRejected(ExpenseReport report) {
        return report.getStatus() == SUBMITTED;
    }

    public static boolean canBeApproved(ExpenseReport report) {
        return report.getStatus() == SUBMITTED;
    }

    public static boolean canBeSettled(ExpenseReport report) {
        return report.getStatus() == APPROVED;
    }

    public static boolean canReportDetailsBeEdited(ExpenseReport report) {
        return !(report.getStatus() == SETTLED);
    }

    public static boolean canReimbursementsBeEdited(ExpenseReport report) {
        return report.getStatus() == SUBMITTED;
    }

    public static boolean canExpensesBeEdited(ExpenseReport report) {
        return report.getStatus() == OPEN;
    }

    /*
     * AUTHORIZATION-BASED RULES
     */
    /**
     * Report details can be edited by the reporter or the assignee.
     *
     * @param report
     * @return
     */
    public boolean canUserEditReport(ExpenseReport report) {
        if (auth.isAdmin()) {
            return true;
        }
        return currentEmployee.equals(report.getAssignee()) || currentEmployee.equals(report.getReporter());
    }

    /**
     * Reimbursements can be edited if the user is an accountant and the report
     * is editable.
     *
     * @param report
     * @return
     */
    public boolean canUserEditReimbursements(ExpenseReport report) {
        if (auth.isAdmin()) {
            return true;
        }
        return canUserEditReport(report)
                && auth.hasRole(ACCOUNTANT);
    }

    /**
     * Reimbursements can be edited if the user is an employee and the report is
     * editable.
     *
     * @param report
     * @return
     */
    public boolean canUserEditExpenses(ExpenseReport report) {
        if (auth.isAdmin()) {
            return true;
        }
        return canUserEditReport(report)
                && auth.hasRole(EMPLOYEE);
    }
}
