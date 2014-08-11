package org.jboss.examples.deltaspike.expensetracker.data;

import java.math.BigDecimal;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryParam;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.criteria.CriteriaSupport;
import org.jboss.examples.deltaspike.expensetracker.model.Employee;
import org.jboss.examples.deltaspike.expensetracker.model.ExpenseReport;
import org.jboss.examples.deltaspike.expensetracker.model.ExpenseReport_;
import org.jboss.examples.deltaspike.expensetracker.model.ReportStatus;

@ApplicationScoped
@Repository
public abstract class ExpenseReportRepository implements EntityRepository<ExpenseReport, Long>, CriteriaSupport<ExpenseReport> {

    public abstract List<ExpenseReport> findByReporter(Employee reporter);

    public abstract List<ExpenseReport> findByAssignee(Employee assignee);

    public List<ExpenseReport> findUnassigned() {
        return criteria()
                .isNull(ExpenseReport_.assignee)
                .getResultList();
    }

    public abstract List<ExpenseReport> findByStatus(ReportStatus status);

    public abstract List<ExpenseReport> findByReporterAndStatus(Employee reporter, ReportStatus status);

    public abstract List<ExpenseReport> findByAssigneeAndStatus(Employee assignee, ReportStatus status);
    
    @Query("SELECT SUM(r.value) FROM Expense e WHERE e.report = :report")
    public abstract BigDecimal getReportExpensesTotal(@QueryParam ExpenseReport report);
    
    @Query("SELECT SUM(r.value) FROM Reimbursement r WHERE r.report = :report")
    public abstract BigDecimal getReportReimbursementTotal(@QueryParam ExpenseReport report);
    
    public BigDecimal getReportBalance(ExpenseReport report) {
        return getReportReimbursementTotal(report).subtract(getReportExpensesTotal(report));
    }
}
