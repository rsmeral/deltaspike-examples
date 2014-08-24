package org.jboss.examples.deltaspike.expensetracker.controller;

import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.deltaspike.core.api.config.view.ViewConfig;
import org.apache.deltaspike.core.api.config.view.navigation.ViewNavigationHandler;
import org.jboss.examples.deltaspike.expensetracker.app.extension.Begin;
import org.jboss.examples.deltaspike.expensetracker.app.extension.Controller;
import org.jboss.examples.deltaspike.expensetracker.app.extension.ViewStack;
import org.jboss.examples.deltaspike.expensetracker.app.message.AppMessages;
import org.jboss.examples.deltaspike.expensetracker.data.PurposeRepository;
import org.jboss.examples.deltaspike.expensetracker.domain.model.Purpose;
import org.jboss.examples.deltaspike.expensetracker.view.SecuredPages;

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

    @Inject
    private AppMessages msg;

    private Purpose selected;

    @Begin(force = true)
    public Class<? extends ViewConfig> create() {
        selected = new Purpose();
        return SecuredPages.Purpose.class;
    }

    @Begin
    public Class<? extends ViewConfig> edit(Purpose purpose) {
        selected = purpose;
        return SecuredPages.Receipt.class;
    }
    
    public void delete(Purpose purpose) {
        repo.remove(purpose);
        faces.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msg.itemDeleted(), null));
    }

    public Class<? extends ViewConfig> save() {
        repo.save(selected);
        faces.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msg.allChangesSaved(), null));
        return viewStack.pop();
    }

    public Class<? extends ViewConfig> cancel() {
        return viewStack.pop();
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
