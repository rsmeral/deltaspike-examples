package org.jboss.examples.deltaspike.expensetracker.view;

import org.jboss.examples.deltaspike.expensetracker.app.extension.ViewStacked;
import org.apache.deltaspike.core.api.config.view.DefaultErrorView;
import org.apache.deltaspike.core.api.config.view.ViewConfig;
import org.apache.deltaspike.jsf.api.config.view.Folder;
import static org.jboss.examples.deltaspike.expensetracker.model.EmployeeRole.ADMIN;
import org.jboss.examples.deltaspike.expensetracker.app.security.LoggedIn;
import org.jboss.examples.deltaspike.expensetracker.app.security.RolesAllowed;

@Folder(name = ".")
@ViewStacked
public interface Pages extends ViewConfig {

    class Login implements Pages {
    }

    class Error extends DefaultErrorView {
    }

    @Folder(name = "/secured")
    @LoggedIn
    interface Secured extends Pages {

        class Home implements Secured {
        }

        class Employee implements Secured {
        }

        class Report implements Secured {
        }

        class Receipt implements Secured {
        }

        class Expense implements Secured {
        }

        class Purpose implements Secured {
        }

        class Reimbursement implements Secured {
        }

        class Reports implements Secured {
        }

        @RolesAllowed(ADMIN)
        interface Admin extends Secured {

            class Employees implements Admin {
            }

            class Purposes implements Admin {
            }

        }

    }
}
