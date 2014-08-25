package org.jboss.examples.deltaspike.expensetracker.controller;

import java.io.Serializable;
import javax.enterprise.event.Event;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.apache.deltaspike.core.api.config.view.ViewConfig;
import org.apache.deltaspike.core.api.config.view.navigation.ViewNavigationHandler;
import org.jboss.examples.deltaspike.expensetracker.app.extension.Begin;
import org.jboss.examples.deltaspike.expensetracker.app.extension.Controller;
import org.jboss.examples.deltaspike.expensetracker.app.extension.End;
import org.jboss.examples.deltaspike.expensetracker.app.extension.ViewStack;
import org.jboss.examples.deltaspike.expensetracker.app.resources.AppMessages;
import org.jboss.examples.deltaspike.expensetracker.data.ExpenseRepository;
import org.jboss.examples.deltaspike.expensetracker.domain.model.Expense;
import org.jboss.examples.deltaspike.expensetracker.domain.model.ExpenseReport;
import org.jboss.examples.deltaspike.expensetracker.view.SecuredPages;

@Controller
public class ExpenseController implements Serializable {

    @Inject
    private ViewNavigationHandler view;

    @Inject
    private ExpenseRepository repo;

    @Inject
    private FacesContext faces;

    @Inject
    private ViewStack viewStack;

    @Inject
    private AppMessages msg;
    
    @Inject
    private Event<ExpenseReportController.Modified> reportModEvent;

    private Expense selected;

    @Begin
    public Class<? extends ViewConfig> create(ExpenseReport report) {
        selected = new Expense();
        selected.setReport(report);
        return SecuredPages.Expense.class;
    }

    @Begin
    public Class<? extends ViewConfig> edit(Expense expense) {
        selected = expense;
        return SecuredPages.Expense.class;
    }

    public void delete(Expense expense) {
        repo.remove(expense);
        faces.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msg.itemDeleted(), null));
    }

    public Class<? extends ViewConfig> save() {
        repo.saveAndFlush(selected);
        reportModEvent.fire(new ExpenseReportController.Modified());
        faces.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msg.allChangesSaved(), null));
        return viewStack.pop();
    }

    @End
    public Class<? extends ViewConfig> cancel() {
        return viewStack.pop();
    }

    public Expense getSelected() {
        return selected;
    }

    public void setSelected(Expense selected) {
        this.selected = selected;
    }

}
