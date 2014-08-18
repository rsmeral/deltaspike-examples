package org.jboss.examples.deltaspike.expensetracker.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.apache.deltaspike.core.api.config.view.navigation.ViewNavigationHandler;
import org.apache.deltaspike.data.api.audit.CurrentUser;
import org.jboss.examples.deltaspike.expensetracker.app.extension.Controller;
import org.jboss.examples.deltaspike.expensetracker.app.extension.End;
import org.jboss.examples.deltaspike.expensetracker.app.extension.ViewStack;
import org.jboss.examples.deltaspike.expensetracker.app.security.view.AuthorizationChecker;
import org.jboss.examples.deltaspike.expensetracker.data.ExpenseReportRepository;
import org.jboss.examples.deltaspike.expensetracker.model.Employee;
import org.jboss.examples.deltaspike.expensetracker.model.ExpenseReport;
import org.jboss.examples.deltaspike.expensetracker.model.ReportStatus;
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
    private AuthorizationChecker idm;

    @Inject
    @CurrentUser
    private Employee currentEmployee;

    private ExpenseReport selected;

    private List<ExpenseReport> list;

    public void create() {
        selected = new ExpenseReport();
        view.navigateTo(Pages.Secured.Report.class);
    }

    public void edit(ExpenseReport report) {
        selected = report;
        view.navigateTo(Pages.Secured.Report.class);
    }

    @End
    public void save() {
        repo.save(selected);
        faces.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "All changes saved.", null));
        view.navigateTo(viewStack.pop());
    }

    public void submit() {
        svc.submit(selected);
        faces.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Report submitted.", null));
        view.navigateTo(Pages.Secured.Report.class);
    }
    
    public void reject() {
        svc.reject(selected);
        faces.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Report rejected.", null));
        view.navigateTo(Pages.Secured.Report.class);
    }
    
    public void approve() {
        svc.approve(selected);
        faces.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Report approved.", null));
        view.navigateTo(Pages.Secured.Report.class);
    }
    
    public void settle() {
        svc.settle(selected);
        faces.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Report settled.", null));
        view.navigateTo(Pages.Secured.Report.class);
    }
    
    public boolean isSubmittable() {
        return selected.getStatus().equals(ReportStatus.OPEN);
    }
    
    public void showAllReportedByCurrentEmployee() {
        list = repo.findByReporter(currentEmployee);
        view.navigateTo(Pages.Secured.Reports.class);
    }
    
    public void showAllAssignedToCurrentAccountant() {
        list = repo.findByAssignee(currentEmployee);
        view.navigateTo(Pages.Secured.Reports.class);
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
