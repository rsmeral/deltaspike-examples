package org.jboss.examples.deltaspike.expensetracker.ftest.pages;

import org.jboss.arquillian.graphene.page.Location;
import org.jboss.examples.deltaspike.expensetracker.ftest.fragments.LoginForm;
import org.openqa.selenium.support.FindBy;
import static org.jboss.examples.deltaspike.expensetracker.ftest.TestConstants.*;

@Location(PAGE_LOGIN)
public class LoginPage extends TemplatePage {

    @FindBy(id = "login:form")
    private LoginForm loginForm;

    public void login(String username, String password) {
        loginForm.login(username, password);
    }

}
