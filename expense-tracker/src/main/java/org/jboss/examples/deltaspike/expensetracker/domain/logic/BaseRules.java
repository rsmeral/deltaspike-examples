package org.jboss.examples.deltaspike.expensetracker.domain.logic;

import javax.enterprise.inject.Typed;
import javax.inject.Inject;
import org.apache.deltaspike.data.api.audit.CurrentUser;
import org.jboss.examples.deltaspike.expensetracker.app.security.Authorizations;
import org.jboss.examples.deltaspike.expensetracker.domain.model.Employee;
import org.jboss.examples.deltaspike.expensetracker.domain.model.ExpenseReport;
import org.jboss.examples.deltaspike.expensetracker.domain.model.ReportStatus;

import static org.jboss.examples.deltaspike.expensetracker.domain.model.EmployeeRole.ACCOUNTANT;
import static org.jboss.examples.deltaspike.expensetracker.domain.model.EmployeeRole.EMPLOYEE;
import static org.jboss.examples.deltaspike.expensetracker.domain.model.ReportStatus.APPROVED;
import static org.jboss.examples.deltaspike.expensetracker.domain.model.ReportStatus.REJECTED;
import static org.jboss.examples.deltaspike.expensetracker.domain.model.ReportStatus.SETTLED;
import static org.jboss.examples.deltaspike.expensetracker.domain.model.ReportStatus.SUBMITTED;

/**
 * Contains state-transfer conditions and authorization rules for
 * ExpenseReports.
 */
@Typed(BaseRules.class)
public class BaseRules implements Rules {

    @Inject
    private Authorizations auth;

    @Inject
    @CurrentUser
    private Employee currentEmployee;

    private static boolean isAssignee(ExpenseReport report, Employee employee) {
        return employee.equals(report.getAssignee());
    }

    private static boolean isReporter(ExpenseReport report, Employee employee) {
        return employee.equals(report.getReporter());
    }

    /*
     * STATE-BASED RULES
     */
    public static boolean isOpenable(ExpenseReport report) {
        return report.getStatus() == null || report.getStatus() == REJECTED;
    }

    public static boolean isAssignable(ExpenseReport report) {
        return report.getStatus() == SUBMITTED && report.getAssignee() == null;
    }

    public static boolean isUnassignable(ExpenseReport report) {
        return report.getStatus() == SUBMITTED && report.getAssignee() != null;
    }

    public static boolean isSubmittable(ExpenseReport report) {
        return report.getStatus() == ReportStatus.OPEN;
    }

    public static boolean isRejectable(ExpenseReport report) {
        return report.getStatus() == SUBMITTED;
    }

    public static boolean isApprovable(ExpenseReport report) {
        return report.getStatus() == SUBMITTED;
    }

    public static boolean isSettleable(ExpenseReport report) {
        return report.getStatus() == APPROVED;
    }

    public static boolean isReportDetailsEditable(ExpenseReport report) {
        return !(report.getStatus() == SETTLED);
    }

    public static boolean isReimbursementsEditable(ExpenseReport report) {
        return report.getStatus() == SUBMITTED;
    }

    public static boolean isExpensesEditable(ExpenseReport report) {
        return report.getStatus() == ReportStatus.OPEN;
    }

    public static boolean isReceiptsEditable(ExpenseReport report) {
        return report.getStatus() != SETTLED && report.getStatus() != REJECTED;
    }

    /*
     * AUTHORIZATION-BASED RULES
     */
    @Override
    public boolean canUserEditReport(ExpenseReport report) {
        return currentEmployee.equals(report.getAssignee()) || currentEmployee.equals(report.getReporter());
    }

    @Override
    public boolean canUserEditReimbursements(ExpenseReport report) {
        return canUserEditReport(report)
                && auth.hasRole(ACCOUNTANT);
    }

    @Override
    public boolean canUserEditExpenses(ExpenseReport report) {
        return canUserEditReport(report)
                && auth.hasRole(EMPLOYEE);
    }

    @Override
    public boolean canUserEditReceipts(ExpenseReport report) {
        return canUserEditReport(report);
    }

    /*
     * COMPLEX RULES
     */
    @Override
    public boolean canBeOpened(@Selected ExpenseReport selected) {
        return canUserEditReport(selected) && isOpenable(selected);
    }

    @Override
    public boolean canBeSubmitted(@Selected ExpenseReport selected) {
        return canUserEditReport(selected) && isSubmittable(selected) && isReporter(selected, currentEmployee);
    }
    
    @Override
    public boolean canBeAssigned(@Selected ExpenseReport selected) {
        return isAssignable(selected) && !isReporter(selected, currentEmployee);
    }

    @Override
    public boolean canBeUnassigned(@Selected ExpenseReport selected) {
        return canUserEditReport(selected) && isUnassignable(selected) && isAssignee(selected, currentEmployee);
    }

    @Override
    public boolean canBeRejected(@Selected ExpenseReport selected) {
        return canUserEditReport(selected) && isRejectable(selected) && isAssignee(selected, currentEmployee);
    }

    @Override
    public boolean canBeApproved(@Selected ExpenseReport selected) {
        return canUserEditReport(selected) && isApprovable(selected) && isAssignee(selected, currentEmployee);
    }

    @Override
    public boolean canBeSettled(@Selected ExpenseReport selected) {
        return canUserEditReport(selected) && isSettleable(selected) && isAssignee(selected, currentEmployee);
    }

    @Override
    public boolean canEditReport(ExpenseReport selected) {
        return canUserEditReport(selected) && isReportDetailsEditable(selected) && isReporter(selected, currentEmployee);
    }

    @Override
    public boolean canEditReimbursements(ExpenseReport selected) {
        return canUserEditReimbursements(selected) && isReimbursementsEditable(selected) && isAssignee(selected, currentEmployee);
    }

    @Override
    public boolean canEditExpenses(ExpenseReport selected) {
        return canUserEditExpenses(selected) && isExpensesEditable(selected) && isReporter(selected, currentEmployee);
    }

    @Override
    public boolean canEditReceipts(ExpenseReport selected) {
        return canUserEditReceipts(selected) && isReceiptsEditable(selected);
    }
}
