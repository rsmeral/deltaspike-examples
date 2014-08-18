package org.jboss.examples.deltaspike.expensetracker.controller;

import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.apache.deltaspike.core.api.config.view.navigation.ViewNavigationHandler;
import org.jboss.examples.deltaspike.expensetracker.app.extension.Controller;
import org.jboss.examples.deltaspike.expensetracker.app.extension.End;
import org.jboss.examples.deltaspike.expensetracker.app.extension.ViewStack;
import org.jboss.examples.deltaspike.expensetracker.app.security.EmployeeService;
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

    private Employee selected;

    private String username;

    private String password;

    private String passwordConfirmation;

    private List<String> roles;

    public void create() {
        selected = new Employee();
        view.navigateTo(Pages.Secured.Employee.class);
    }

    public void edit(Employee employee) {
        selected = employee;
        username = svc.getUserForEmployee(employee).getLoginName();
        view.navigateTo(Pages.Secured.Employee.class);
    }

    @End
    public void save() {
        if (isNewEmployee()) {
            svc.registerEmployee(repo.save(selected), username, password, (String[]) roles.toArray());
            faces.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Employee created.", null));
        } else {
            repo.save(selected);
            faces.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "All changes saved.", null));
        }
        view.navigateTo(viewStack.pop());
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
