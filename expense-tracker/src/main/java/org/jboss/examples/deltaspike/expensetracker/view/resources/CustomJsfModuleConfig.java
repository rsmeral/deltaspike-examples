package org.jboss.examples.deltaspike.expensetracker.view.resources;

import javax.enterprise.inject.Specializes;
import org.apache.deltaspike.jsf.api.config.JsfModuleConfig;
import org.apache.deltaspike.jsf.spi.scope.window.ClientWindowConfig;

/*
 * Disables the dswid URL parameter by disabling window scope completely.
 */
@Specializes
public class CustomJsfModuleConfig extends JsfModuleConfig {

    @Override
    public ClientWindowConfig.ClientWindowRenderMode getDefaultWindowMode() {
        return ClientWindowConfig.ClientWindowRenderMode.NONE;
    }

}
