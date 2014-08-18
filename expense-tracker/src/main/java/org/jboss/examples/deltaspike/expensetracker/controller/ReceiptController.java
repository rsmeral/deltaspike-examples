package org.jboss.examples.deltaspike.expensetracker.controller;

import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.apache.deltaspike.core.api.config.view.ViewConfig;
import org.apache.deltaspike.core.api.config.view.navigation.ViewNavigationHandler;
import org.jboss.examples.deltaspike.expensetracker.app.extension.Controller;
import org.jboss.examples.deltaspike.expensetracker.app.extension.End;
import org.jboss.examples.deltaspike.expensetracker.app.extension.ViewStack;
import org.jboss.examples.deltaspike.expensetracker.data.ReceiptRepository;
import org.jboss.examples.deltaspike.expensetracker.model.Receipt;
import org.jboss.examples.deltaspike.expensetracker.view.Pages;

@Controller
public class ReceiptController implements Serializable {

    @Inject
    private ViewNavigationHandler view;

    @Inject
    private ReceiptRepository repo;

    @Inject
    private FacesContext faces;
    
    @Inject
    private ViewStack viewStack;

    private Receipt selected;

    public Class<? extends ViewConfig> create() {
        selected = new Receipt();
        return Pages.Secured.Receipt.class;
    }
    
    public Class<? extends ViewConfig> edit(Receipt receipt) {
        selected = receipt;
        return Pages.Secured.Receipt.class;
    }
    
    public void delete(Receipt receipt) {
        repo.remove(receipt);
        faces.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "All changes saved.", null));
    }
    
    public Class<? extends ViewConfig> show(Receipt receipt) {
        selected = receipt;
        return Pages.Secured.Receipt.class;// TODO
    }
    
    @End
    public Class<? extends ViewConfig> save() {
        repo.save(selected);
        faces.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "All changes saved.", null));
        return viewStack.pop();
    }

    public Receipt getSelected() {
        return selected;
    }

    public void setSelected(Receipt selected) {
        this.selected = selected;
    }

}
