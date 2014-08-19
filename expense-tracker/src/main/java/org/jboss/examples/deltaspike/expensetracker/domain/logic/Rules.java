package org.jboss.examples.deltaspike.expensetracker.domain.logic;

import javax.inject.Named;
import org.jboss.examples.deltaspike.expensetracker.domain.model.ExpenseReport;
import static org.jboss.examples.deltaspike.expensetracker.domain.model.ReportStatus.*;

/**
 * Contains state-transfer rules for ExpenseReports.
 */
@Named
public class Rules {

    public boolean canOpen(ExpenseReport report) {
        return report.getStatus() == null || report.getStatus() == REJECTED;
    }

    public boolean canSubmit(ExpenseReport report) {
        return report.getStatus() == OPEN;
    }

    public boolean canReject(ExpenseReport report) {
        return report.getStatus() == SUBMITTED;
    }

    public boolean canApprove(ExpenseReport report) {
        return report.getStatus() == SUBMITTED;
    }

    public boolean canSettle(ExpenseReport report) {
        return report.getStatus() == APPROVED;
    }
    
    public boolean canEditReport(ExpenseReport report) {
        return !(report.getStatus() == SETTLED);
    }
    
    public boolean canEditReimbursements(ExpenseReport report) {
        return report.getStatus() == SUBMITTED;
    }
    
    public boolean canEditExpenses(ExpenseReport report) {
        return report.getStatus() == OPEN;
    }
}
