package org.jboss.examples.deltaspike.expensetracker.service;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import org.apache.deltaspike.jpa.api.transaction.Transactional;
import static org.jboss.examples.deltaspike.expensetracker.app.security.EmployeeRole.*;
import org.jboss.examples.deltaspike.expensetracker.data.ExpenseReportRepository;
import org.jboss.examples.deltaspike.expensetracker.model.Employee;
import org.jboss.examples.deltaspike.expensetracker.model.ExpenseReport;
import org.jboss.examples.deltaspike.expensetracker.model.ReportStatus;
import org.picketlink.authorization.annotations.LoggedIn;
import org.picketlink.authorization.annotations.RolesAllowed;

@Transactional
@LoggedIn
@RequestScoped
public class ExpenseReportService {

    @Inject
    private ExpenseReportRepository repo;

    @RolesAllowed(ACCOUNTANT)
    public void assign(ExpenseReport report, Employee assignee) {
        if (report == null || assignee == null) {
            throw new IllegalArgumentException();
        }

        if (report.getAssignee() != null) {
            throw new RuntimeException("Report is already assigned");
        }

        if (report.getReporter().equals(assignee)) {
            throw new IllegalStateException("You can't assign a report to yourself");
        }

        if (report.getStatus() == ReportStatus.APPROVED
                || report.getStatus() == ReportStatus.REJECTED
                || report.getStatus() == ReportStatus.SETTLED) {
            throw new IllegalStateException("Report is already resolved");
        }

        report.setAssignee(assignee);

        repo.save(report);
    }

    @RolesAllowed(EMPLOYEE)
    public void submit(ExpenseReport report) {
        if (report == null) {
            throw new IllegalArgumentException();
        }

        ReportStatus status = report.getStatus();
        if (status == ReportStatus.SUBMITTED || status == ReportStatus.APPROVED || status == ReportStatus.SETTLED) {
            throw new IllegalStateException("Report has already been submitted");
        }

        if (report.getAssignee() != null && status != ReportStatus.REJECTED) {
            throw new IllegalStateException("Report is already assigned");
        }

        report.setStatus(ReportStatus.SUBMITTED);
        
        repo.save(report);
    }

    @RolesAllowed(ACCOUNTANT)
    public void reject(ExpenseReport report) {
        if (report == null) {
            throw new IllegalArgumentException("null");
        }
        if (report.getAssignee() == null) {
            throw new RuntimeException("No assignee");
        }

        report.setStatus(ReportStatus.REJECTED);
        repo.save(report);
    }

    @RolesAllowed(ACCOUNTANT)
    public void settle(ExpenseReport report) {
        if (report == null) {
            throw new IllegalArgumentException("null");
        }
        if (report.getStatus() != ReportStatus.APPROVED) {
            throw new RuntimeException("Report is not approved!");
        }

        report.setStatus(ReportStatus.SETTLED);
        repo.save(report);
    }

    @RolesAllowed(ACCOUNTANT)
    public void approve(ExpenseReport report) {
        if (report == null) {
            throw new IllegalArgumentException("null");
        }
        if (report.getAssignee() == null) {
            throw new RuntimeException("No assignee");
        }

        report.setStatus(ReportStatus.APPROVED);
        repo.save(report);
    }
}
