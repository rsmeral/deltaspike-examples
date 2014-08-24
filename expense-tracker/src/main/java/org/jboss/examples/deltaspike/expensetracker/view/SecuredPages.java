package org.jboss.examples.deltaspike.expensetracker.view;

import org.apache.deltaspike.core.api.config.view.ViewConfig;
import org.apache.deltaspike.jsf.api.config.view.Folder;
import org.apache.deltaspike.jsf.api.config.view.View;
import org.jboss.examples.deltaspike.expensetracker.app.extension.ViewStacked;
import org.jboss.examples.deltaspike.expensetracker.app.security.LoggedIn;
import org.jboss.examples.deltaspike.expensetracker.app.security.RolesAllowed;
import static org.jboss.examples.deltaspike.expensetracker.domain.model.EmployeeRole.ADMIN;

@ViewStacked
@LoggedIn
@Folder(name = "/secured")
@View(navigation = View.NavigationMode.REDIRECT)
public interface SecuredPages extends ViewConfig {

    
    class Home implements SecuredPages {
    }

    class Employee implements SecuredPages {
    }

    class Report implements SecuredPages {
    }

    class Receipt implements SecuredPages {
    }

    class Expense implements SecuredPages {
    }

    class Purpose implements SecuredPages {
    }

    class Reimbursement implements SecuredPages {
    }

    class Reports implements SecuredPages {
    }

    @RolesAllowed(ADMIN)
    interface Admin extends SecuredPages {

        class Employees implements Admin {
        }

        class Purposes implements Admin {
        }

    }

}
