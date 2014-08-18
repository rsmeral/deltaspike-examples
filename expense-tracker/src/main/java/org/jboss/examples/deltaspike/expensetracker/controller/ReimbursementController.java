package org.jboss.examples.deltaspike.expensetracker.controller;

import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.apache.deltaspike.core.api.config.view.navigation.ViewNavigationHandler;
import org.jboss.examples.deltaspike.expensetracker.app.extension.Controller;
import org.jboss.examples.deltaspike.expensetracker.app.extension.End;
import org.jboss.examples.deltaspike.expensetracker.app.extension.ViewStack;
import org.jboss.examples.deltaspike.expensetracker.data.ReimbursementRepository;
import org.jboss.examples.deltaspike.expensetracker.model.ExpenseReport;
import org.jboss.examples.deltaspike.expensetracker.model.Reimbursement;
import org.jboss.examples.deltaspike.expensetracker.view.Pages;

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

    private Reimbursement selected;

    public void create(ExpenseReport report) {
        selected = new Reimbursement();
        selected.setReport(report);
        view.navigateTo(Pages.Secured.Reimbursement.class);
    }
    
    public void edit(Reimbursement reimbursement) {
        selected = reimbursement;
        view.navigateTo(Pages.Secured.Reimbursement.class);
    }
    
    public void delete(Reimbursement reimbursement) {
        repo.remove(reimbursement);
        faces.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "All changes saved.", null));
    }
    
    @End
    public void save() {
        repo.save(selected);
        faces.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "All changes saved.", null));
        view.navigateTo(viewStack.pop());
    }

    public Reimbursement getSelected() {
        return selected;
    }

    public void setSelected(Reimbursement selected) {
        this.selected = selected;
    }

}
