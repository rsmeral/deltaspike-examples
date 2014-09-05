package org.jboss.examples.deltaspike.expensetracker.app.extension;

import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.apache.deltaspike.core.api.config.view.metadata.ViewConfigResolver;

public class CurrentViewConfigProducer {

    @Inject
    private ViewConfigResolver viewConfigResolver;

    @Inject
    private FacesContext faces;

    @Produces
    @CurrentView
    public Class produceCurrentViewConfig() {
        return viewConfigResolver.getViewConfigDescriptor(faces.getViewRoot().getViewId()).getConfigClass();
    }
}
