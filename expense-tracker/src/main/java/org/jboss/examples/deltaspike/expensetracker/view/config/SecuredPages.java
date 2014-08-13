package org.jboss.examples.deltaspike.expensetracker.view.config;

import org.apache.deltaspike.core.api.config.view.ViewConfig;
import org.apache.deltaspike.jsf.api.config.view.Folder;
import static org.jboss.examples.deltaspike.expensetracker.app.security.EmployeeRole.ADMIN;
import org.jboss.examples.deltaspike.expensetracker.app.security.view.LoggedIn;
import org.jboss.examples.deltaspike.expensetracker.app.security.view.RolesAllowed;

@Folder(name = "/secured")
@LoggedIn
public interface SecuredPages extends ViewConfig {
    
    class Reports implements SecuredPages {}
    
    @RolesAllowed(ADMIN)
    interface Admin extends SecuredPages {
        
        class Employees implements Admin {}
        
        class Purposes implements Admin {}
        
    }
    
}
