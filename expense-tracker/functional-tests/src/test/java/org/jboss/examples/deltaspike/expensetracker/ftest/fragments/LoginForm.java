package org.jboss.examples.deltaspike.expensetracker.ftest.fragments;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginForm {

    @FindBy(id = "login:form:username")
    private WebElement usernameInput;

    @FindBy(id = "login:form:password")
    private WebElement passwordInput;

    @FindBy(id = "login:form:loginBtn")
    private WebElement loginButton;

    public void login(String username, String password) {
        usernameInput.clear();
        usernameInput.sendKeys(username);
        passwordInput.clear();
        passwordInput.sendKeys(password);

        loginButton.click();
    }

}
