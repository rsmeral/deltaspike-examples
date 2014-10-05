package org.jboss.examples.deltaspike.expensetracker.domain.logic;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import org.apache.deltaspike.data.api.audit.CurrentUser;
import org.apache.deltaspike.security.api.authorization.Secures;
import org.jboss.examples.deltaspike.expensetracker.app.security.Authorizations;
import org.jboss.examples.deltaspike.expensetracker.domain.model.Employee;
import org.jboss.examples.deltaspike.expensetracker.domain.model.ExpenseReport;
import org.jboss.examples.deltaspike.expensetracker.domain.model.ReportStatus;

import static org.jboss.examples.deltaspike.expensetracker.domain.logic.Operation.Type.APPROVE;
import static org.jboss.examples.deltaspike.expensetracker.domain.logic.Operation.Type.ASSIGN;
import static org.jboss.examples.deltaspike.expensetracker.domain.logic.Operation.Type.REJECT;
import static org.jboss.examples.deltaspike.expensetracker.domain.logic.Operation.Type.SETTLE;
import static org.jboss.examples.deltaspike.expensetracker.domain.logic.Operation.Type.SUBMIT;
import static org.jboss.examples.deltaspike.expensetracker.domain.logic.Operation.Type.UNASSIGN;
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
@Named
@SessionScoped
@AdminAllowed
public class Rules implements Serializable {

    /*
     * Short circuit the rules to always return true for admin role.
     */
    @AdminAllowed
    @Interceptor
    public static class AdminInterceptor implements Serializable {

        @Inject
        private Authorizations auth;

        @AroundInvoke
        public Object invoke(InvocationContext ctx) throws Exception {
            if (auth.isAdmin()) {
                return true;
            }
            return ctx.proceed();
        }
    }

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
    public boolean canUserEditReport(ExpenseReport report) {
        return currentEmployee.equals(report.getAssignee()) || currentEmployee.equals(report.getReporter());
    }

    public boolean canUserEditReimbursements(ExpenseReport report) {
        return canUserEditReport(report)
                && auth.hasRole(ACCOUNTANT);
    }

    public boolean canUserEditExpenses(ExpenseReport report) {
        return canUserEditReport(report)
                && auth.hasRole(EMPLOYEE);
    }

    public boolean canUserEditReceipts(ExpenseReport report) {
        return canUserEditReport(report);
    }

    /*
     * COMPLEX RULES
     */
    @Secures
    @Operation(Operation.Type.OPEN)
    public boolean canBeOpened(@Selected ExpenseReport selected) {
        return canUserEditReport(selected) && isOpenable(selected);
    }

    @Secures
    @Operation(SUBMIT)
    public boolean canBeSubmitted(@Selected ExpenseReport selected) {
        return canUserEditReport(selected) && isSubmittable(selected) && isReporter(selected, currentEmployee);
    }

    @Secures
    @Operation(ASSIGN)
    public boolean canBeAssigned(@Selected ExpenseReport selected) {
        return isAssignable(selected) && !isReporter(selected, currentEmployee);
    }

    @Secures
    @Operation(UNASSIGN)
    public boolean canBeUnassigned(@Selected ExpenseReport selected) {
        return canUserEditReport(selected) && isUnassignable(selected) && isAssignee(selected, currentEmployee);
    }

    @Secures
    @Operation(REJECT)
    public boolean canBeRejected(@Selected ExpenseReport selected) {
        return canUserEditReport(selected) && isRejectable(selected) && isAssignee(selected, currentEmployee);
    }

    @Secures
    @Operation(APPROVE)
    public boolean canBeApproved(@Selected ExpenseReport selected) {
        return canUserEditReport(selected) && isApprovable(selected) && isAssignee(selected, currentEmployee);
    }

    @Secures
    @Operation(SETTLE)
    public boolean canBeSettled(@Selected ExpenseReport selected) {
        return canUserEditReport(selected) && isSettleable(selected) && isAssignee(selected, currentEmployee);
    }

    public boolean canEditReport(ExpenseReport selected) {
        return canUserEditReport(selected) && isReportDetailsEditable(selected) && isReporter(selected, currentEmployee);
    }

    public boolean canEditReimbursements(ExpenseReport selected) {
        return canUserEditReimbursements(selected) && isReimbursementsEditable(selected) && isAssignee(selected, currentEmployee);
    }

    public boolean canEditExpenses(ExpenseReport selected) {
        return canUserEditExpenses(selected) && isExpensesEditable(selected) && isReporter(selected, currentEmployee);
    }

    public boolean canEditReceipts(ExpenseReport selected) {
        return canUserEditReceipts(selected) && isReceiptsEditable(selected);
    }
}
