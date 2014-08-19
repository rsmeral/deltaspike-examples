package org.jboss.examples.deltaspike.expensetracker.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.apache.deltaspike.core.api.config.view.ViewConfig;
import org.apache.deltaspike.core.api.config.view.navigation.ViewNavigationHandler;
import org.apache.deltaspike.data.api.audit.CurrentUser;
import org.jboss.examples.deltaspike.expensetracker.app.exception.ApplicationException;
import org.jboss.examples.deltaspike.expensetracker.app.extension.Controller;
import org.jboss.examples.deltaspike.expensetracker.app.extension.End;
import org.jboss.examples.deltaspike.expensetracker.app.extension.ViewStack;
import org.jboss.examples.deltaspike.expensetracker.app.message.AppMessages;
import org.jboss.examples.deltaspike.expensetracker.app.security.Authorizations;
import org.jboss.examples.deltaspike.expensetracker.data.ExpenseReportRepository;
import org.jboss.examples.deltaspike.expensetracker.domain.logic.Rules;
import org.jboss.examples.deltaspike.expensetracker.domain.model.Employee;
import org.jboss.examples.deltaspike.expensetracker.domain.model.ExpenseReport;
import org.jboss.examples.deltaspike.expensetracker.domain.model.ReportStatus;
import org.jboss.examples.deltaspike.expensetracker.service.ExpenseReportService;
import org.jboss.examples.deltaspike.expensetracker.view.Pages;

@Controller
public class ExpenseReportController implements Serializable {

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
    private Authorizations idm;

    @Inject
    private Rules rules;

    @Inject
    private AppMessages msg;

    @Inject
    @CurrentUser
    private Employee currentEmployee;

    private ExpenseReport selected;

    private List<ExpenseReport> list;

    public Class<? extends ViewConfig> create() {
        selected = new ExpenseReport();
        selected.setStatus(ReportStatus.OPEN);
        return Pages.Secured.Report.class;
    }

    public Class<? extends ViewConfig> open() {
        selected.setStatus(ReportStatus.OPEN);
        return Pages.Secured.Report.class;
    }
    
    public Class<? extends ViewConfig> edit(ExpenseReport report) {
        selected = report;
        return Pages.Secured.Report.class;
    }

    @End
    public Class<? extends ViewConfig> save() {
        repo.save(selected);
        faces.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msg.allChangesSaved(), null));
        return viewStack.pop();
    }

    @End
    public Class<? extends ViewConfig> submit() throws ApplicationException {
        svc.submit(selected);
        faces.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msg.reportSubmitted(selected.getName()), null));
        return Pages.Secured.Report.class;
    }

    @End
    public Class<? extends ViewConfig> reject() throws ApplicationException {
        svc.reject(selected);
        faces.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msg.reportRejected(selected.getName()), null));
        return Pages.Secured.Report.class;
    }

    @End
    public Class<? extends ViewConfig> approve() throws ApplicationException {
        svc.approve(selected);
        faces.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msg.reportApproved(selected.getName()), null));
        return Pages.Secured.Report.class;
    }

    @End
    public Class<? extends ViewConfig> settle() throws ApplicationException {
        svc.settle(selected);
        faces.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msg.reportSettled(selected.getName()), null));
        return Pages.Secured.Report.class;
    }

    @End
    public Class<? extends ViewConfig> cancel() {
        return viewStack.pop();
    }

    public boolean isCanBeOpened() {
        return idm.canEditReport(selected) && rules.canOpen(selected);
    }

    public boolean isCanBeSubmitted() {
        return idm.canEditReport(selected) && rules.canSubmit(selected);
    }

    public boolean isCanBeRejected() {
        return idm.canEditReport(selected) && rules.canReject(selected);
    }

    public boolean isCanBeApproved() {
        return idm.canEditReport(selected) && rules.canApprove(selected);
    }

    public boolean isCanBeSettled() {
        return idm.canEditReport(selected) && rules.canSettle(selected);
    }

    public boolean isReportEditable() {
        return idm.canEditReport(selected) && rules.canEditReport(selected);
    }
    
    public boolean isReimbursementsEditable() {
        return idm.canEditReimbursements(selected) && rules.canEditReimbursements(selected);
    }

    public boolean isExpensesEditable() {
        return idm.canEditExpenses(selected) && rules.canEditExpenses(selected);
    }

    public Class<? extends ViewConfig> showAllReportedByCurrentEmployee() {
        list = repo.findByReporter(currentEmployee);
        return Pages.Secured.Reports.class;
    }

    public Class<? extends ViewConfig> showAllAssignedToCurrentAccountant() {
        list = repo.findByAssignee(currentEmployee);
        return Pages.Secured.Reports.class;
    }

    public BigDecimal getExpensesTotal() {
        return repo.getReportExpensesTotal(selected);
    }

    public BigDecimal getReimbursementsTotal() {
        return repo.getReportReimbursementTotal(selected);
    }

    public BigDecimal getReportTotal() {
        return repo.getReportBalance(selected);
    }

    public ExpenseReport getSelected() {
        return selected;
    }

    public void setSelected(ExpenseReport selected) {
        this.selected = selected;
    }

    public List<ExpenseReport> getList() {
        return list;
    }

    public void setList(List<ExpenseReport> list) {
        this.list = list;
    }

}
