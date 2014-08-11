package org.jboss.examples.deltaspike.expensetracker.view;

import org.apache.deltaspike.core.api.config.view.ViewConfig;
import org.apache.deltaspike.jsf.api.config.view.Folder;
import static org.jboss.examples.deltaspike.expensetracker.app.security.EmployeeRole.ADMIN_ROLE;
import org.jboss.examples.deltaspike.expensetracker.app.security.pl.LoggedIn;
import org.jboss.examples.deltaspike.expensetracker.app.security.pl.PicketLinkSecured;
import org.jboss.examples.deltaspike.expensetracker.app.security.pl.RolesAllowed;

@Folder(name = "/secured")
@PicketLinkSecured
@LoggedIn
public interface SecuredPages extends ViewConfig {
    
    class Reports implements SecuredPages {}
    
    @RolesAllowed(ADMIN_ROLE)
    interface Admin extends SecuredPages {
        
        class Employees implements Admin {}
        
        class Purposes implements Admin {}
        
    }
    
}
