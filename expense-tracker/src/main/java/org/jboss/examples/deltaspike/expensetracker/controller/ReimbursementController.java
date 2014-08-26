package org.jboss.examples.deltaspike.expensetracker.controller;

import java.io.Serializable;
import javax.enterprise.event.Event;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.apache.deltaspike.core.api.config.view.ViewConfig;
import org.apache.deltaspike.core.api.config.view.navigation.ViewNavigationHandler;
import org.apache.deltaspike.data.api.audit.CurrentUser;
import org.apache.deltaspike.jsf.api.message.JsfMessage;
import org.jboss.examples.deltaspike.expensetracker.app.extension.Begin;
import org.jboss.examples.deltaspike.expensetracker.app.extension.Controller;
import org.jboss.examples.deltaspike.expensetracker.app.extension.End;
import org.jboss.examples.deltaspike.expensetracker.app.extension.ViewStack;
import org.jboss.examples.deltaspike.expensetracker.app.resources.AppMessages;
import org.jboss.examples.deltaspike.expensetracker.data.ReimbursementRepository;
import org.jboss.examples.deltaspike.expensetracker.domain.model.Employee;
import org.jboss.examples.deltaspike.expensetracker.domain.model.ExpenseReport;
import org.jboss.examples.deltaspike.expensetracker.domain.model.Reimbursement;
import org.jboss.examples.deltaspike.expensetracker.view.SecuredPages;

@Controller
public class ReimbursementController implements Serializable {

    @Inject
    private ViewNavigationHandler view;

    @Inject
    private ReimbursementRepository repo;

    @Inject
    private FacesContext faces;

    @Inject
    private ViewStack viewStack;

    @Inject
    private JsfMessage<AppMessages> msg;
    
    @Inject
    @CurrentUser
    private Employee currentEmployee;
    
    @Inject
    private Event<ExpenseReportController.Modified> reportModEvent;
    
    private Reimbursement selected;

    @Begin
    public Class<? extends ViewConfig> create(ExpenseReport report) {
        selected = new Reimbursement();
        selected.setReport(report);
        selected.setCreator(currentEmployee);
        return SecuredPages.Reimbursement.class;
    }

    @Begin
    public Class<? extends ViewConfig> edit(Reimbursement reimbursement) {
        selected = reimbursement;
        return SecuredPages.Reimbursement.class;
    }

    public void delete(Reimbursement reimbursement) {
        repo.remove(reimbursement);
        msg.addInfo().itemDeleted();
    }

    public Class<? extends ViewConfig> save() {
        repo.saveAndFlush(selected);
        reportModEvent.fire(new ExpenseReportController.Modified());
        msg.addInfo().allChangesSaved();
        return viewStack.pop();
    }

    @End
    public Class<? extends ViewConfig> cancel() {
        return viewStack.pop();
    }

    public Reimbursement getSelected() {
        return selected;
    }

    public void setSelected(Reimbursement selected) {
        this.selected = selected;
    }

}
