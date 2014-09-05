package org.jboss.examples.deltaspike.expensetracker.ftest.pages;

import java.util.ArrayList;
import java.util.List;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.graphene.page.Location;
import static org.jboss.examples.deltaspike.expensetracker.ftest.util.TestConstants.*;
import org.jboss.examples.deltaspike.expensetracker.ftest.fragments.PasswordForm;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import org.richfaces.fragment.message.RichFacesMessage;

@Location(PAGE_EMPLOYEE)
public class EmployeePage extends TemplatePage {

    @Drone
    private WebDriver driver;

    // Password forms
    @FindBy(id = "changePassword:form")
    private PasswordForm changePasswordForm;

    @FindBy(id = "employeeForm:newPassword:passwordPanelGrid")
    private PasswordForm newPasswordForm;

    // Username
    @FindBy(id = "employeeForm:username")
    private WebElement username;

    @FindBy(id = "employeeForm:usernameMessage")
    private RichFacesMessage usernameMsg;

    // Roles
    @FindBy(id = "employeeForm:roles")
    private WebElement roles;

    @FindBy(id = "employeeForm:rolesMessage")
    private RichFacesMessage rolesMsg;

    // First name
    @FindBy(id = "employeeForm:firstName")
    private WebElement firstName;

    @FindBy(id = "employeeForm:firstNameMessage")
    private RichFacesMessage firstNameMsg;

    // Last name
    @FindBy(id = "employeeForm:lastName")
    private WebElement lastName;

    @FindBy(id = "employeeForm:lastNameMessage")
    private RichFacesMessage lastNameMsg;

    // Bank Account
    @FindBy(id = "employeeForm:bankAccount")
    private WebElement bankAccount;

    @FindBy(id = "employeeForm:bankAccountMessage")
    private RichFacesMessage bankAccountMsg;

    // Email
    @FindBy(id = "employeeForm:email")
    private WebElement email;

    @FindBy(id = "employeeForm:emailMessage")
    private RichFacesMessage emailMsg;

    // Buttons
    @FindBy(id = "employeeForm:saveEmployeeBtn")
    private WebElement saveEmployeeBtn;

    @FindBy(id = "employeeForm:cancelBtn")
    private WebElement cancelBtn;

    public void setPassword(String password, String confirmation) {
        newPasswordForm.setPassword(password, confirmation);
    }

    public void changePassword(String password, String confirmation) {
        changePasswordForm.changePassword(password, confirmation);
    }

    public void setUser(String username, String... roles) {
        if (username != null) {
            this.username.clear();
            this.username.sendKeys(username);
        }

        if (roles.length > 0) {
            Select rolesSelect = new Select(this.roles);
            rolesSelect.deselectAll();
            Actions actions = new Actions(driver).keyDown(Keys.CONTROL);
            for (String role : roles) {
                for (WebElement option : rolesSelect.getOptions()) {
                    if (option.getText().contains(role)) {
                        actions.click(option);
                    }
                }
            }
            actions.keyUp(Keys.CONTROL).perform();
        }

    }

    public List<String> getRoles() {
        Select rolesSelect = new Select(this.roles);
        List<String> rolesList = new ArrayList<String>();
        for (WebElement option : rolesSelect.getOptions()) {
            rolesList.add(option.getText());
        }
        return rolesList;
    }

    public void setEmployeeDetails(String firstName, String lastName, String bankAccount, String email) {
        if (firstName != null) {
            this.firstName.clear();
            this.firstName.sendKeys(firstName);
        }

        if (lastName != null) {
            this.lastName.clear();
            this.lastName.sendKeys(lastName);
        }

        if (bankAccount != null) {
            this.bankAccount.clear();
            this.bankAccount.sendKeys(bankAccount);
        }

        if (email != null) {
            this.email.clear();
            this.email.sendKeys(email);
        }
    }

    public void save() {
        saveEmployeeBtn.click();
    }

    public void cancel() {
        cancelBtn.click();
    }

    public PasswordForm getChangePasswordForm() {
        return changePasswordForm;
    }

    public PasswordForm getNewPasswordForm() {
        return newPasswordForm;
    }

    public RichFacesMessage getUsernameMsg() {
        return usernameMsg;
    }

    public RichFacesMessage getRolesMsg() {
        return rolesMsg;
    }

    public RichFacesMessage getFirstNameMsg() {
        return firstNameMsg;
    }

    public RichFacesMessage getLastNameMsg() {
        return lastNameMsg;
    }

    public RichFacesMessage getBankAccountMsg() {
        return bankAccountMsg;
    }

    public RichFacesMessage getEmailMsg() {
        return emailMsg;
    }

    public WebElement getUsernameElement() {
        return username;
    }

    public WebElement getRolesElement() {
        return roles;
    }

    public WebElement getFirstName() {
        return firstName;
    }

    public WebElement getLastName() {
        return lastName;
    }

    public WebElement getBankAccount() {
        return bankAccount;
    }

    public WebElement getEmail() {
        return email;
    }

}
