package org.jboss.examples.deltaspike.expensetracker.app.resources;

import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.jboss.examples.deltaspike.expensetracker.data.PurposeRepository;
import org.jboss.examples.deltaspike.expensetracker.domain.model.Purpose;

public class Resources {

    @Produces
    @Named("purposes")
    public List<Purpose> getAllPurposes(PurposeRepository repo) {
        return repo.findAll();
    }

    @Produces
    @RequestScoped
    public FacesContext getFacesContext() {
        return FacesContext.getCurrentInstance();
    }

}
