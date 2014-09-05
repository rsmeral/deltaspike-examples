package org.jboss.examples.deltaspike.expensetracker.app.extension;

import java.io.Serializable;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.Stereotype;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.apache.deltaspike.core.api.config.view.controller.PostRenderView;
import org.apache.deltaspike.core.api.config.view.controller.ViewControllerRef;
import org.apache.deltaspike.core.api.config.view.metadata.ViewMetaData;

/**
 * Every view annotated with ViewStacked will be pushed on to the
 * {@link ViewStack} on navigation.
 */
@Stereotype
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@ViewMetaData
@ViewControllerRef(ViewStacked.Controller.class)
public @interface ViewStacked {

    @SessionScoped
    public static class Controller implements Serializable {

        @Inject
        private FacesContext faces;

        @Inject
        @CurrentView
        private Instance<Class> currentView;

        @Inject
        private ViewStack viewStack;

        @PostRenderView
        public void pushViewToStack() {
            viewStack.push(currentView.get());
        }

    }

}
