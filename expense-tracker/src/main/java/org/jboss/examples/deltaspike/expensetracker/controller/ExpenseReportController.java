package org.jboss.examples.deltaspike.expensetracker.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
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
import org.jboss.examples.deltaspike.expensetracker.data.EmployeeRepository;
import org.jboss.examples.deltaspike.expensetracker.data.ExpenseReportRepository;
import org.jboss.examples.deltaspike.expensetracker.data.ReceiptRepository;
import org.jboss.examples.deltaspike.expensetracker.domain.model.Employee;
import org.jboss.examples.deltaspike.expensetracker.domain.model.ExpenseReport;
import org.jboss.examples.deltaspike.expensetracker.domain.model.Receipt;
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
    private ReceiptRepository receiptRepo;

    @Inject
    private EmployeeRepository employeeRepo;

    @Inject
    private ExpenseReportService svc;

    @Inject
    private ViewStack viewStack;

    @Inject
    private JsfMessage<AppMessages> jsfMsg;

    @Inject
    private AppMessages msg;

    @Inject
    @CurrentUser
    private Employee currentEmployee;

    private ExpenseReport selected;

    @Inject
    private ReportListHolder reportList;

    private boolean needRefresh = false;

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
        jsfMsg.addInfo().allChangesSaved();
        return SecuredPages.Report.class;
    }

    @Begin
    public Class<? extends ViewConfig> open() throws ApplicationException {
        svc.open(selected);
        return SecuredPages.Report.class;
    }

    @End
    public Class<? extends ViewConfig> submit() throws ApplicationException {
        svc.submit(selected);
        jsfMsg.addInfo().reportSubmitted(selected.getName());
        return viewStack.pop();
    }

    @End
    public Class<? extends ViewConfig> reject() throws ApplicationException {
        svc.reject(selected);
        jsfMsg.addInfo().reportRejected(selected.getName());
        return viewStack.pop();
    }

    @End
    public Class<? extends ViewConfig> approve() throws ApplicationException {
        svc.approve(selected);
        jsfMsg.addInfo().reportApproved(selected.getName());
        return viewStack.pop();
    }

    @End
    public Class<? extends ViewConfig> settle() throws ApplicationException {
        svc.settle(selected);
        jsfMsg.addInfo().reportSettled(selected.getName());
        return viewStack.pop();
    }

    @End
    public Class<? extends ViewConfig> cancel() {
        return viewStack.pop();
    }

    /*
     * REPORT LISTS
     */
    @Begin
    public Class<? extends ViewConfig> showAllReportedByCurrentEmployee() {
        reportList.setListTitle(msg.allReportedBy(currentEmployee.getFullName()));
        reportList.setQuery(repo.findByReporter(currentEmployee));
        return SecuredPages.Reports.class;
    }

    @Begin
    public Class<? extends ViewConfig> showAllAssignedToCurrentAccountant() {
        reportList.setListTitle(msg.allAssignedTo(currentEmployee.getFullName()));
        reportList.setQuery(repo.findByAssignee(currentEmployee));
        return SecuredPages.Reports.class;
    }

    @Begin
    public Class<? extends ViewConfig> showAllReports() {
        reportList.setListTitle(msg.allReports());
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
        return report == null || report.getId() == null;
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
     * ASSIGNEE AUTOCOMPLETE
     */
    public List<Employee> suggestEmployee(String namePart) {
        return employeeRepo.findByPartOfName("%" + namePart + "%");
    }

    /*
     * Observe modification events to refresh report
     */
    public void refreshReportOnModification(@Observes Modified event) {
        needRefresh = true;
    }

    public void doRefresh() {

        if (needRefresh) {
            repo.refresh(selected);
            needRefresh = false;
        }
    }

    public List<Receipt> getReportReceipts() {
        return receiptRepo.findByReport(selected);
    }

}
