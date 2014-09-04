package org.jboss.examples.deltaspike.expensetracker.ftest.fragments;

import org.jboss.arquillian.graphene.findby.FindByJQuery;
import org.openqa.selenium.WebElement;

public class EmployeesListRow {

    @FindByJQuery("td[id$='employeeUsername']")
    private WebElement username;

    @FindByJQuery("td[id$='employeeName']")
    private WebElement name;

    @FindByJQuery("td[id$='employeeEmail']")
    private WebElement email;

    @FindByJQuery("td[id$='employeeBankAccount']")
    private WebElement bankAccount;

    @FindByJQuery("input[id$='editEmployeeBtn']")
    private WebElement editEmployeeLink;

    public WebElement getUsername() {
        return username;
    }

    public WebElement getName() {
        return name;
    }

    public WebElement getEmail() {
        return email;
    }

    public WebElement getBankAccount() {
        return bankAccount;
    }

    public void editEmployee() {
        editEmployeeLink.click();
    }
    
}
