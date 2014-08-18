package org.jboss.examples.deltaspike.expensetracker.controller;

import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.apache.deltaspike.core.api.config.view.ViewConfig;
import org.apache.deltaspike.core.api.config.view.navigation.ViewNavigationHandler;
import org.jboss.examples.deltaspike.expensetracker.app.extension.Controller;
import org.jboss.examples.deltaspike.expensetracker.app.extension.End;
import org.jboss.examples.deltaspike.expensetracker.app.extension.ViewStack;
import org.jboss.examples.deltaspike.expensetracker.app.message.AppMessages;
import org.jboss.examples.deltaspike.expensetracker.service.EmployeeService;
import org.jboss.examples.deltaspike.expensetracker.data.EmployeeRepository;
import org.jboss.examples.deltaspike.expensetracker.model.Employee;
import org.jboss.examples.deltaspike.expensetracker.view.Pages;

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
    private AppMessages msg;

    private Employee selected;

    private String username;

    private String password;

    private String passwordConfirmation;

    private List<String> roles;

    public Class<? extends ViewConfig> create() {
        selected = new Employee();
        return Pages.Secured.Employee.class;
    }

    public Class<? extends ViewConfig> edit(Employee employee) {
        selected = employee;
        username = svc.getUserForEmployee(employee).getLoginName();
        return Pages.Secured.Employee.class;
    }

    @End
    public Class<? extends ViewConfig> save() {
        if (isNewEmployee()) {
            svc.registerEmployee(repo.save(selected), username, password, (String[]) roles.toArray());
            faces.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msg.employeeCreated(selected.getFirstName(), selected.getLastName()), null));
        } else {
            repo.save(selected);
            faces.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msg.allChangesSaved(), null));
        }
        return viewStack.pop();
    }

    private boolean isNewEmployee() {
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
