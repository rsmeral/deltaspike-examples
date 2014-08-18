package org.jboss.examples.deltaspike.expensetracker.controller;

import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.deltaspike.core.api.config.view.navigation.ViewNavigationHandler;
import org.jboss.examples.deltaspike.expensetracker.app.extension.Controller;
import org.jboss.examples.deltaspike.expensetracker.app.extension.End;
import org.jboss.examples.deltaspike.expensetracker.app.extension.ViewStack;
import org.jboss.examples.deltaspike.expensetracker.data.PurposeRepository;
import org.jboss.examples.deltaspike.expensetracker.model.Purpose;
import org.jboss.examples.deltaspike.expensetracker.view.Pages;

@Controller
public class PurposeController implements Serializable {

    @Inject
    private ViewNavigationHandler view;

    @Inject
    private PurposeRepository repo;

    @Inject
    private FacesContext faces;
    
    @Inject
    private ViewStack viewStack;

    private Purpose selected;

    public void create() {
        selected = new Purpose();
        view.navigateTo(Pages.Secured.Purpose.class);
    }
    
    public void edit(Purpose purpose) {
        selected = purpose;
        view.navigateTo(Pages.Secured.Receipt.class);
    }
    
    public void delete(Purpose purpose) {
        repo.remove(purpose);
        faces.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "All changes saved.", null));
    }
    
    @End
    public void save() {
        repo.save(selected);
        faces.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "All changes saved.", null));
        view.navigateTo(viewStack.pop());
    }

    public Purpose getSelected() {
        return selected;
    }

    public void setSelected(Purpose selected) {
        this.selected = selected;
    }
    
    @Named("purposes")
    public List<Purpose> getAllPurposes() {
        return repo.findAll();
    }

}
