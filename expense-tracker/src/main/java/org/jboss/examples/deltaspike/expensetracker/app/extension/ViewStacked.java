package org.jboss.examples.deltaspike.expensetracker.app.extension;

import java.io.Serializable;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Stereotype;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.apache.deltaspike.core.api.config.view.ViewConfig;
import org.apache.deltaspike.core.api.config.view.controller.PostRenderView;
import org.apache.deltaspike.core.api.config.view.controller.ViewControllerRef;
import org.apache.deltaspike.core.api.config.view.metadata.ViewConfigResolver;
import org.apache.deltaspike.core.api.config.view.metadata.ViewMetaData;

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
        private ViewConfigResolver viewConfigResolver;
        
        @Inject
        private ViewStack viewStack;
        
        @PostRenderView
        public void pushViewToStack() {
            Class<? extends ViewConfig> configClass = viewConfigResolver.getViewConfigDescriptor(faces.getViewRoot().getViewId()).getConfigClass();
            viewStack.push(configClass);
        }
        
    }

}
