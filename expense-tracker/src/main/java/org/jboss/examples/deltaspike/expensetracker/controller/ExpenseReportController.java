package org.jboss.examples.deltaspike.expensetracker.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.deltaspike.core.api.config.view.ViewConfig;
import org.apache.deltaspike.core.api.config.view.navigation.ViewNavigationHandler;
import org.apache.deltaspike.data.api.audit.CurrentUser;
import org.apache.deltaspike.jsf.api.message.JsfMessage;
import org.jboss.examples.deltaspike.expensetracker.app.exception.ApplicationException;
import org.jboss.examples.deltaspike.expensetracker.app.extension.Begin;
import org.jboss.examples.deltaspike.expensetracker.app.extension.Controller;
import org.jboss.examples.deltaspike.expensetracker.app.extension.End;
import org.jboss.examples.deltaspike.expensetracker.app.extension.ViewStack;
import org.jboss.examples.deltaspike.expensetracker.app.resources.AppMessages;
import org.jboss.examples.deltaspike.expensetracker.data.ExpenseReportRepository;
import org.jboss.examples.deltaspike.expensetracker.domain.model.Employee;
import org.jboss.examples.deltaspike.expensetracker.domain.model.ExpenseReport;
import org.jboss.examples.deltaspike.expensetracker.domain.model.ReportStatus;
import org.jboss.examples.deltaspike.expensetracker.service.ExpenseReportService;
import org.jboss.examples.deltaspike.expensetracker.view.SecuredPages;

@Controller
public class ExpenseReportController implements Serializable {

    public static class Modified {
    }

    @Inject
    private ViewNavigationHandler view;

    @Inject
    private ExpenseReportRepository repo;

    @Inject
    private ExpenseReportService svc;

    @Inject
    private FacesContext faces;

    @Inject
    private ViewStack viewStack;

    @Inject
    private JsfMessage<AppMessages> msg;

    @Inject
    @CurrentUser
    private Employee currentEmployee;

    @Produces
    @Named("selectedReport")
    private ExpenseReport selected;

    @Inject
    private ReportListHolder reportList;

    /*
     * OPERATIONS
     */
    @Begin(force = true)
    public Class<? extends ViewConfig> create() {
        selected = new ExpenseReport();
        selected.setReporter(currentEmployee);
        selected.setStatus(ReportStatus.OPEN);
        return SecuredPages.Report.class;
    }

    @Begin
    public Class<? extends ViewConfig> edit(ExpenseReport report) {
        selected = report;
        return SecuredPages.Report.class;
    }

    public void assign(Employee employee) throws ApplicationException {
        svc.assign(selected, employee);
    }

    public void unassign() throws ApplicationException {
        svc.unassign(selected);
    }

    public Class<? extends ViewConfig> save() {
        repo.save(selected);
        msg.addInfo().allChangesSaved();
        return SecuredPages.Report.class;
    }

    @Begin
    public Class<? extends ViewConfig> open() {
        selected.setStatus(ReportStatus.OPEN);
        return SecuredPages.Report.class;
    }

    @End
    public Class<? extends ViewConfig> submit() throws ApplicationException {
        svc.submit(selected);
        msg.addInfo().reportSubmitted(selected.getName());
        return viewStack.pop();
    }

    @End
    public Class<? extends ViewConfig> reject() throws ApplicationException {
        svc.reject(selected);
        msg.addInfo().reportRejected(selected.getName());
        return viewStack.pop();
    }

    @End
    public Class<? extends ViewConfig> approve() throws ApplicationException {
        svc.approve(selected);
        msg.addInfo().reportApproved(selected.getName());
        return viewStack.pop();
    }

    @End
    public Class<? extends ViewConfig> settle() throws ApplicationException {
        svc.settle(selected);
        msg.addInfo().reportSettled(selected.getName());
        return viewStack.pop();
    }

    @End
    public Class<? extends ViewConfig> cancel() {
        return viewStack.pop();
    }

    @Begin
    public Class<? extends ViewConfig> showAllReportedByCurrentEmployee() {
        reportList.setListTitle("All reported by " + currentEmployee.getFullName());
        reportList.setQuery(repo.findByReporter(currentEmployee));
        return SecuredPages.Reports.class;
    }

    @Begin
    public Class<? extends ViewConfig> showAllAssignedToCurrentAccountant() {
        reportList.setListTitle("All assigned to " + currentEmployee.getFullName());
        reportList.setQuery(repo.findByAssignee(currentEmployee));
        return SecuredPages.Reports.class;
    }

    @Begin
    public Class<? extends ViewConfig> showAllReports() {
        reportList.setListTitle("All reports");
        reportList.setQuery(repo.findAllReports());
        return SecuredPages.Reports.class;
    }

    /*
     * DATA ACCESS
     */
    public BigDecimal getExpensesTotal(ExpenseReport report) {
        if (isNew(report)) {
            return BigDecimal.ZERO;
        }
        BigDecimal reportExpensesTotal = repo.getReportExpensesTotal(report);
        return reportExpensesTotal == null ? BigDecimal.ZERO : reportExpensesTotal;
    }

    public BigDecimal getReimbursementsTotal(ExpenseReport report) {
        if (isNew(report)) {
            return BigDecimal.ZERO;
        }
        BigDecimal reportReimbursementTotal = repo.getReportReimbursementTotal(report);
        return reportReimbursementTotal == null ? BigDecimal.ZERO : reportReimbursementTotal;
    }

    public BigDecimal getReportTotal(ExpenseReport report) {
        if (isNew(report)) {
            return BigDecimal.ZERO;
        }
        return getReimbursementsTotal(report).subtract(getExpensesTotal(report));
    }

    public List<ExpenseReport> getAllUnsettledForCurrentEmployee() {
        return repo.findAllUnsettledByReporter(currentEmployee);
    }

    public List<ExpenseReport> getAllUnsettledForCurrentAccountant() {
        return repo.findAllUnsettledByAssignee(currentEmployee);
    }

    public List<ExpenseReport> getAllUnassigned() {
        return repo.findUnassigned();
    }

    public List<ExpenseReport> getAllUnsettled() {
        return repo.findAllUnsettled();
    }

    public boolean isNew(ExpenseReport report) {
        return report.getId() == null;
    }

    /*
     * ACCESSORS
     */
    public ExpenseReport getSelected() {
        return selected;
    }

    public void setSelected(ExpenseReport selected) {
        this.selected = selected;
    }

    /*
     * Observe modification events to refresh report
     */
    public void refreshReportOnModification(@Observes Modified event) {
        repo.refresh(selected);
    }
}
