package org.jboss.examples.deltaspike.expensetracker.ftest;

import org.jboss.examples.deltaspike.expensetracker.ftest.util.ExpenseTrackerFunctionalTestBase;
import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.graphene.page.InitialPage;
import org.jboss.arquillian.graphene.page.Page;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.examples.deltaspike.expensetracker.ftest.pages.HomePage;
import org.jboss.examples.deltaspike.expensetracker.ftest.pages.LoginPage;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.jboss.examples.deltaspike.expensetracker.ftest.util.TestConstants.*;
import org.jboss.examples.deltaspike.expensetracker.ftest.pages.EmployeesPage;

@RunWith(Arquillian.class)
public class LoginTest extends ExpenseTrackerFunctionalTestBase {

    @Page
    private HomePage homePage;

    @Test
    public void correctLoginWorks(@InitialPage LoginPage loginPage) {
        loginPage.login(USER_EMPLOYEE, USER_EMPLOYEE);
        assertTrue("Should be home after login", getLocation().contains(PAGE_HOME));
        assertEquals("User's full name should be on the page after login", NAME_EMPLOYEE, homePage.getToolbar().getEmployeeName());

        homePage.logout();
        assertTrue("Should be on login screen after logout", getLocation().contains(PAGE_LOGIN));
    }

    @Test
    public void badCredentialsDontLogin(@InitialPage LoginPage loginPage) {
        loginPage.login(USER_EMPLOYEE, "bad password");
        assertTrue("Should be on login screen after bad login", getLocation().contains(PAGE_LOGIN));
        assertTrue(loginPage.isGlobalMessagePresent(MSG_BAD_LOGIN));

        loginPage.login("bad user", "bad password");
        assertTrue("Should be on login screen after bad login", getLocation().contains(PAGE_LOGIN));
        assertTrue(loginPage.isGlobalMessagePresent(MSG_BAD_LOGIN));
    }

    @Test
    public void loginRedirectsToHome(@InitialPage LoginPage loginPage) {
        loginPage.login(USER_EMPLOYEE, USER_EMPLOYEE);
        assertTrue("Should be home after login, but is " + getLocation(), getLocation().contains(PAGE_HOME));

        Graphene.goTo(LoginPage.class);
        assertTrue("Login screen should redirect home when user is logged in", getLocation().contains(PAGE_HOME));
        homePage.logout();
    }

    @Test
    public void deniedPageRequestRemembered(@InitialPage LoginPage loginPage) {
        Graphene.goTo(EmployeesPage.class);
        assertTrue(loginPage.isGlobalMessagePresent(MSG_NO_AUTH_ROLE));

        loginPage.login(USER_ADMIN, USER_ADMIN);
        assertTrue(getLocation().contains(PAGE_EMPLOYEES));
    }

}
