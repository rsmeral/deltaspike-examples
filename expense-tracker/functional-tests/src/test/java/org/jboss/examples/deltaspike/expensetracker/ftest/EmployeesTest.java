package org.jboss.examples.deltaspike.expensetracker.ftest;

import org.jboss.examples.deltaspike.expensetracker.ftest.util.ExpenseTrackerFunctionalTestBase;
import java.text.MessageFormat;
import org.jboss.arquillian.graphene.page.Page;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.examples.deltaspike.expensetracker.ftest.fragments.EmployeeList;
import org.jboss.examples.deltaspike.expensetracker.ftest.pages.EmployeesPage;
import org.jboss.examples.deltaspike.expensetracker.ftest.pages.HomePage;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.jboss.examples.deltaspike.expensetracker.ftest.util.TestConstants.*;

import org.jboss.examples.deltaspike.expensetracker.ftest.pages.EmployeePage;
import org.jboss.examples.deltaspike.expensetracker.ftest.pages.LoginPage;

/*
 * Tests in this class are sequenced only because adding an employee is an
 * irreversible operation in the application. 
 */
@RunWith(Arquillian.class)
public class EmployeesTest extends ExpenseTrackerFunctionalTestBase {

    @Page
    private EmployeesPage employeesPage;

    @Page
    private EmployeePage employeePage;

    @Page
    private LoginPage loginPage;

    @Page
    private HomePage homePage;

    @Test
    @InSequence(1)
    public void employeeShouldntSeeAdminOperations() {
        login(USER_EMPLOYEE, USER_EMPLOYEE);

        assertFalse("Non-admin shouldn't see employees link", homePage.getToolbar().isEmployeesLinkPresent());
        assertFalse("Non-admin shouldn't see purposes link", homePage.getToolbar().isPurposesLinkPresent());

        homePage.getToolbar().editProfile();

        assertFalse("Username should not be editable", employeePage.getUsernameElement().isEnabled());
        assertFalse("Roles should not be editable by non-admin", employeePage.getRolesElement().isEnabled());
    }

    @Test
    @InSequence(2)
    public void passwordMatchValidationWorks() {
        login(USER_EMPLOYEE, USER_EMPLOYEE);
        homePage.getToolbar().editProfile();

        employeePage.changePassword("asdf", "asdfghjk");
        assertNotNull("Validation error message should appear when passwords don't match", employeePage.getChangePasswordForm().getValidatorMessages().getItem(MSG_PWD_DONT_MATCH));

        employeePage.changePassword("asdf", "asdf");
        assertEquals("There should be no validator message when passwords match", 0, employeePage.getChangePasswordForm().getValidatorMessages().getItems().size());
        assertTrue(employeePage.isGlobalMessagePresent(MSG_PWD_CHANGED));

        // return to original state
        employeePage.changePassword(USER_EMPLOYEE, USER_EMPLOYEE);
    }

    @Test
    @InSequence(3)
    public void employeeDetailsChange() {
        login(USER_EMPLOYEE, USER_EMPLOYEE);

        final String NEW_FIRST_NAME = "Jonathan";
        final String NEW_LAST_NAME = "Smith";

        homePage.getToolbar().editProfile();

        employeePage.setEmployeeDetails(NEW_FIRST_NAME, NEW_LAST_NAME, null, null);
        employeePage.save();

        assertTrue(employeePage.isGlobalMessagePresent(MSG_CHANGES_SAVED));
        assertEquals("New employee name should be in the toolbar", NEW_FIRST_NAME + " " + NEW_LAST_NAME, employeePage.getToolbar().getEmployeeName());
        assertTrue("Should be home after Save on Employee page", getLocation().contains(PAGE_HOME));

        // reset
        homePage.getToolbar().editProfile();
        employeePage.setEmployeeDetails(NAME_EMPLOYEE_FIRST, NAME_EMPLOYEE_LAST, null, null);
        employeePage.save();

    }

    @Test
    @InSequence(4)
    public void employeesPageContainsInitialData() {
        login(USER_ADMIN, USER_ADMIN);

        homePage.getToolbar().goToEmployees();
        assertEquals(3, employeesPage.getEmployeesTable().getAllRows().size());

        EmployeeList.Row employee = employeesPage.getEmployeesTable().getEmployeeByUsername(USER_EMPLOYEE);
        assertEquals(NAME_EMPLOYEE, employee.getName().getText());

        EmployeeList.Row admin = employeesPage.getEmployeesTable().getEmployeeByUsername(USER_ADMIN);
        assertEquals(NAME_ADMIN, admin.getName().getText());

        EmployeeList.Row accountant = employeesPage.getEmployeesTable().getEmployeeByUsername(USER_ACCOUNTANT);
        assertEquals(NAME_ACCOUNTANT, accountant.getName().getText());
    }

    @Test
    @InSequence(5)
    public void changeUserRole() {
        login(USER_ADMIN, USER_ADMIN);

        homePage.getToolbar().goToEmployees();
        EmployeeList.Row employee = employeesPage.getEmployeesTable().getEmployeeByUsername(USER_EMPLOYEE);
        employee.editEmployee();
        assertTrue(getLocation().contains(PAGE_EMPLOYEE));

        employeePage.setUser(null, ROLE_EMPLOYEE, ROLE_ADMIN);
        employeePage.save();
        assertTrue(employeePage.isGlobalMessagePresent(MSG_CHANGES_SAVED));
        assertTrue(getLocation().contains(PAGE_EMPLOYEES));

        employeesPage.getToolbar().logout();

        loginPage.login(USER_EMPLOYEE, USER_EMPLOYEE);
        assertTrue(homePage.getToolbar().isEmployeesLinkPresent());

        // reset
        homePage.getToolbar().editProfile();
        employeePage.setUser(null, ROLE_EMPLOYEE);
        employeePage.save();
    }

    @Test
    @InSequence(6)
    public void addEmployee() {
        login(USER_ADMIN, USER_ADMIN);

        final String NEW_PASSWORD = "password";
        final String NEW_USERNAME = "foo";
        final String NEW_FIRST_NAME = "Foo";
        final String NEW_LAST_NAME = "Bar";
        final String NEW_EMAIL = "foo@bar.com";
        final String NEW_BANK_ACCOUNT = "123123123";

        homePage.getToolbar().goToEmployees();
        employeesPage.addEmployee();
        assertTrue(getLocation().contains(PAGE_EMPLOYEE));

        employeePage.setPassword(NEW_PASSWORD, NEW_PASSWORD);
        employeePage.setEmployeeDetails(NEW_FIRST_NAME, NEW_LAST_NAME, NEW_BANK_ACCOUNT, NEW_EMAIL);
        // try existing username
        employeePage.setUser(USER_EMPLOYEE, ROLE_EMPLOYEE);

        employeePage.save();
        assertTrue(employeePage.getUsernameMsg().advanced().isVisible());
        assertEquals(MSG_USERNAME_TAKEN, employeePage.getUsernameMsg().getDetail());

        employeePage.setUser(NEW_USERNAME);
        employeePage.setPassword(NEW_PASSWORD, NEW_PASSWORD);
        employeePage.save();

        assertTrue(employeesPage.isGlobalMessagePresent(MessageFormat.format(MSG_EMPLOYEE_CREATED, NEW_FIRST_NAME, NEW_LAST_NAME)));
        assertEquals(NEW_EMAIL, employeesPage.getEmployeesTable().getEmployeeByUsername(NEW_USERNAME).getEmail().getText());

        employeesPage.getToolbar().logout();
        loginPage.login(NEW_USERNAME, NEW_PASSWORD);

        assertEquals(NEW_FIRST_NAME + " " + NEW_LAST_NAME, employeePage.getToolbar().getEmployeeName());

    }

}
