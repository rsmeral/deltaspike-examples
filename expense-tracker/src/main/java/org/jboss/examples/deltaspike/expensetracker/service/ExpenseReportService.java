package org.jboss.examples.deltaspike.expensetracker.service;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import org.jboss.examples.deltaspike.expensetracker.app.exception.ApplicationException;
import org.jboss.examples.deltaspike.expensetracker.app.resources.AppMessages;
import org.jboss.examples.deltaspike.expensetracker.data.ExpenseReportRepository;
import org.jboss.examples.deltaspike.expensetracker.domain.logic.Operation;
import org.jboss.examples.deltaspike.expensetracker.domain.logic.Selected;
import org.jboss.examples.deltaspike.expensetracker.domain.model.Employee;
import org.jboss.examples.deltaspike.expensetracker.domain.model.ExpenseReport;
import org.jboss.examples.deltaspike.expensetracker.domain.model.ReportStatus;
import org.picketlink.authorization.annotations.LoggedIn;
import org.picketlink.authorization.annotations.RolesAllowed;

import static org.jboss.examples.deltaspike.expensetracker.domain.logic.Operation.Type.APPROVE;
import static org.jboss.examples.deltaspike.expensetracker.domain.logic.Operation.Type.ASSIGN;
import static org.jboss.examples.deltaspike.expensetracker.domain.logic.Operation.Type.OPEN;
import static org.jboss.examples.deltaspike.expensetracker.domain.logic.Operation.Type.REJECT;
import static org.jboss.examples.deltaspike.expensetracker.domain.logic.Operation.Type.SETTLE;
import static org.jboss.examples.deltaspike.expensetracker.domain.logic.Operation.Type.SUBMIT;
import static org.jboss.examples.deltaspike.expensetracker.domain.logic.Operation.Type.UNASSIGN;
import static org.jboss.examples.deltaspike.expensetracker.domain.model.EmployeeRole.ACCOUNTANT;
import static org.jboss.examples.deltaspike.expensetracker.domain.model.EmployeeRole.EMPLOYEE;

@LoggedIn
@RequestScoped
//@Transactional
public class ExpenseReportService {

    @Inject
    private ExpenseReportRepository repo;

    @Inject
    private AppMessages msg;

    @RolesAllowed(EMPLOYEE)
    @Operation(OPEN)
    public void open(@Selected ExpenseReport report) throws ApplicationException {
        if (report == null) {
            throw new IllegalArgumentException();
        }

        report.setStatus(ReportStatus.OPEN);
        repo.save(report);
    }

    @RolesAllowed(ACCOUNTANT)
    @Operation(ASSIGN)
    public void assign(@Selected ExpenseReport report, @Selected Employee assignee) throws ApplicationException {
        if (report == null || assignee == null) {
            throw new IllegalArgumentException();
        }

        report.setAssignee(assignee);
        repo.save(report);
    }

    @RolesAllowed(ACCOUNTANT)
    @Operation(UNASSIGN)
    public void unassign(@Selected ExpenseReport report) throws ApplicationException {
        if (report == null) {
            throw new IllegalArgumentException();
        }

        report.setAssignee(null);
        repo.save(report);
    }

    @RolesAllowed(EMPLOYEE)
    @Operation(SUBMIT)
    public void submit(@Selected ExpenseReport report) throws ApplicationException {
        if (report == null) {
            throw new IllegalArgumentException();
        }

        report.setStatus(ReportStatus.SUBMITTED);
        repo.save(report);
    }

    @RolesAllowed(ACCOUNTANT)
    @Operation(REJECT)
    public void reject(@Selected ExpenseReport report) throws ApplicationException {
        if (report == null) {
            throw new IllegalArgumentException();
        }

        report.setStatus(ReportStatus.REJECTED);
        repo.save(report);
    }

    @RolesAllowed(ACCOUNTANT)
    @Operation(APPROVE)
    public void approve(@Selected ExpenseReport report) throws ApplicationException {
        if (report == null) {
            throw new IllegalArgumentException();
        }

        report.setStatus(ReportStatus.APPROVED);
        repo.save(report);
    }

    @RolesAllowed(ACCOUNTANT)
    @Operation(SETTLE)
    public void settle(@Selected ExpenseReport report) throws ApplicationException {
        if (report == null) {
            throw new IllegalArgumentException();
        }

        report.setStatus(ReportStatus.SETTLED);
        repo.save(report);
    }
}
