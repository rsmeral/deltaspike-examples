package org.jboss.examples.deltaspike.expensetracker.controller;

import java.io.Serializable;
import org.apache.deltaspike.core.api.config.view.ViewConfig;
import org.jboss.examples.deltaspike.expensetracker.app.extension.Controller;
import org.jboss.examples.deltaspike.expensetracker.app.extension.End;
import org.jboss.examples.deltaspike.expensetracker.view.SecuredPages;

@Controller
public class NavigationController implements Serializable {
    
    @End
    public Class<? extends ViewConfig> goHome() {
        return SecuredPages.Home.class;
    }
    
    @End
    public Class<? extends ViewConfig> goToPurposes() {
        return SecuredPages.Admin.Purposes.class;
    }
    
    @End
    public Class<? extends ViewConfig> goToEmployees() {
        return SecuredPages.Admin.Employees.class;
    }
    
}
