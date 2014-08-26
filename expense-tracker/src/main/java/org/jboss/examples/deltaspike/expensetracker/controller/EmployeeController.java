package org.jboss.examples.deltaspike.expensetracker.controller;

import java.io.Serializable;
import java.util.List;
import javax.enterprise.event.Event;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Size;
import org.apache.deltaspike.core.api.config.view.ViewConfig;
import org.apache.deltaspike.core.api.config.view.navigation.ViewNavigationHandler;
import org.apache.deltaspike.data.api.audit.CurrentUser;
import org.apache.deltaspike.jsf.api.message.JsfMessage;
import org.jboss.examples.deltaspike.expensetracker.app.extension.Begin;
import org.jboss.examples.deltaspike.expensetracker.app.extension.Controller;
import org.jboss.examples.deltaspike.expensetracker.app.extension.End;
import org.jboss.examples.deltaspike.expensetracker.app.extension.ViewStack;
import org.jboss.examples.deltaspike.expensetracker.app.resources.AppMessages;
import org.jboss.examples.deltaspike.expensetracker.app.resources.CurrentEmployee;
import org.jboss.examples.deltaspike.expensetracker.data.EmployeeRepository;
import org.jboss.examples.deltaspike.expensetracker.domain.model.Employee;
import org.jboss.examples.deltaspike.expensetracker.service.EmployeeService;
import org.jboss.examples.deltaspike.expensetracker.view.SecuredPages;
import org.jboss.examples.deltaspike.expensetracker.view.resources.NotTaken;

@Controller
public class EmployeeController implements Serializable {

    @Inject
    private ViewNavigationHandler view;

    @Inject
    private EmployeeRepository repo;

    @Inject
    private EmployeeService svc;

    @Inject
    private FacesContext faces;

    @Inject
    private ViewStack viewStack;

    @Inject
    private PasswordHolder pwdHolder;

    @Inject
    private JsfMessage<AppMessages> msg;

    @Inject
    @CurrentUser
    private Employee currentEmployee;
    
    @Inject
    private Event<CurrentEmployee.Modified> employeeModEvent;

    private Employee selected;

    @NotTaken
    private String username;

    private List<String> roles;

    @Begin
    public Class<? extends ViewConfig> create() {
        selected = new Employee();
        return SecuredPages.Employee.class;
    }

    @Begin
    public Class<? extends ViewConfig> edit(Employee employee) {
        selected = employee;
        roles = svc.getRoles(employee);
        username = svc.getUserForEmployee(employee).getLoginName();
        return SecuredPages.Employee.class;
    }

    public Class<? extends ViewConfig> save() {
        if (isNewEmployee()) {
            svc.registerEmployee(repo.save(selected), username, pwdHolder.getPassword(), roles.toArray(new String[roles.size()]));
            msg.addInfo().employeeCreated(selected.getFirstName(), selected.getLastName());
        } else {
            svc.setRoles(selected, roles.toArray(new String[roles.size()]));
            repo.save(selected);
            msg.addInfo().allChangesSaved();
            if (selected.equals(currentEmployee)) {
                employeeModEvent.fire(new CurrentEmployee.Modified());
            }
        }
        return viewStack.pop();
    }

    @End
    public Class<? extends ViewConfig> cancel() {
        return viewStack.pop();
    }

    public List<Employee> getAllEmployees() {
        return repo.findAll();
    }

    public boolean isNewEmployee() {
        return selected.getId() == null;
    }

    public Employee getSelected() {
        return selected;
    }

    public void setSelected(Employee selected) {
        this.selected = selected;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    @Controller
    public static class PasswordHolder implements Serializable {

        @Inject
        private FacesContext faces;

        @Inject
        private EmployeeService svc;

        @Inject
        private JsfMessage<AppMessages> msg;

        @Size(min = 4)
        private String password;

        private String passwordConfirmation;

        public void changePassword(Employee employee, String password) {
            svc.changePassword(employee, password);
            msg.addInfo().passwordChanged();
        }

        @AssertTrue(message = "Passwords don't match")
        public boolean isPasswordsEqual() {
            return password != null && password.equals(passwordConfirmation);
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getPasswordConfirmation() {
            return passwordConfirmation;
        }

        public void setPasswordConfirmation(String passwordConfirmation) {
            this.passwordConfirmation = passwordConfirmation;
        }
    }

}
