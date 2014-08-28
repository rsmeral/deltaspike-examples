package org.jboss.examples.deltaspike.expensetracker.app.resources;

import javax.inject.Named;
import org.apache.deltaspike.core.api.message.MessageBundle;
import org.apache.deltaspike.core.api.message.MessageTemplate;

/**
 * Type-safe messages for the application. The actual messages are located in a
 * properties file in resources, in the same package as this class.
 *
 * Can also be referenced in views using EL.
 *
 * Or they can be easily used as Faces messages using
 * {@link org.apache.deltaspike.jsf.api.message.JsfMessage}, instead of manually
 * calling {@link javax.faces.context.FacesContext#addMessage}.
 */
@Named
@MessageBundle
public interface AppMessages {

    @MessageTemplate("{security.noUserIsLoggedIn}")
    String noUserIsLoggedIn();

    @MessageTemplate("{security.noRolesAuthorized}")
    String noRolesAuthorized();

    @MessageTemplate("{security.passwordChanged}")
    String passwordChanged();

    @MessageTemplate("{controller.employeeCreated}")
    String employeeCreated(String firstName, String lastName);

    @MessageTemplate("{controller.allChangesSaved}")
    String allChangesSaved();

    @MessageTemplate("{controller.itemDeleted}")
    String itemDeleted();

    @MessageTemplate("{controller.reportSubmitted}")
    String reportSubmitted(String reportName);

    @MessageTemplate("{controller.reportRejected}")
    String reportRejected(String reportName);

    @MessageTemplate("{controller.reportApproved}")
    String reportApproved(String reportName);

    @MessageTemplate("{controller.reportSettled}")
    String reportSettled(String reportName);

    @MessageTemplate("{service.reportIsAlreadyAssigned}")
    String reportAlreadyAssigned(String reportName);

    @MessageTemplate("{service.reportNotAssigned}")
    String reportNotAssigned(String reportName);

    @MessageTemplate("{service.reportIsAlreadySubmitted}")
    String reportAlreadySubmitted(String reportName);

    @MessageTemplate("{service.cantAssignReportToSelf}")
    String cantAssignReportToSelf();

    @MessageTemplate("{service.reportNotApproved}")
    String reportNotApproved(String reportName);

    @MessageTemplate("{service.reportCantBeReopened}")
    String reportCantBeReopened(String reportName);

    @MessageTemplate("{security.loginFailed}")
    String loginFailed();

    @MessageTemplate("{security.loggedOutSuccessfully}")
    String loggedOutSuccessfully();

}
