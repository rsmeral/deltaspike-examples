package org.jboss.examples.deltaspike.expensetracker.controller;

import java.io.Serializable;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.apache.deltaspike.core.api.config.view.ViewConfig;
import org.apache.deltaspike.core.api.config.view.navigation.ViewNavigationHandler;
import org.apache.deltaspike.jsf.api.message.JsfMessage;
import org.jboss.examples.deltaspike.expensetracker.app.extension.Begin;
import org.jboss.examples.deltaspike.expensetracker.app.extension.Controller;
import org.jboss.examples.deltaspike.expensetracker.app.extension.ViewStack;
import org.jboss.examples.deltaspike.expensetracker.app.resources.AppMessages;
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
    private JsfMessage<AppMessages> msg;

    private Purpose selected;

    @Begin
    public Class<? extends ViewConfig> create() {
        selected = new Purpose();
        return SecuredPages.Purpose.class;
    }

    @Begin
    public Class<? extends ViewConfig> edit(Purpose purpose) {
        selected = purpose;
        return SecuredPages.Purpose.class;
    }
    
    public void delete(Purpose purpose) {
        repo.remove(purpose);
        msg.addInfo().itemDeleted();
    }

    public Class<? extends ViewConfig> save() {
        repo.save(selected);
        msg.addInfo().allChangesSaved();
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

}
