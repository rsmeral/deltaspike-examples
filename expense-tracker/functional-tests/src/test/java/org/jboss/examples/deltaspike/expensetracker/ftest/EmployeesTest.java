package org.jboss.examples.deltaspike.expensetracker.ftest;

import org.jboss.arquillian.graphene.page.Page;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.examples.deltaspike.expensetracker.ftest.pages.EmployeesPage;
import org.jboss.examples.deltaspike.expensetracker.ftest.pages.HomePage;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.jboss.examples.deltaspike.expensetracker.ftest.TestConstants.*;
import org.jboss.examples.deltaspike.expensetracker.ftest.fragments.EmployeesListRow;
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
        login(EMPLOYEE_USER, EMPLOYEE_USER);

        assertFalse("Non-admin shouldn't see employees link", homePage.getToolbar().isEmployeesLinkPresent());
        assertFalse("Non-admin shouldn't see purposes link", homePage.getToolbar().isPurposesLinkPresent());

        homePage.getToolbar().editProfile();

        assertFalse("Username should not be editable", employeePage.getUsernameElement().isEnabled());
        assertFalse("Roles should not be editable by non-admin", employeePage.getRolesElement().isEnabled());
    }

    @Test
    @InSequence(2)
    public void passwordMatchValidationWorks() {
        login(EMPLOYEE_USER, EMPLOYEE_USER);
        homePage.getToolbar().editProfile();

        employeePage.changePassword("asdf", "asdfghjk");
        assertNotNull("Validation error message should appear when passwords don't match", employeePage.getChangePasswordForm().getValidatorMessages().getItem("Passwords don't match"));

        employeePage.changePassword("asdf", "asdf");
        assertEquals("There should be no validator message when passwords match", 0, employeePage.getChangePasswordForm().getValidatorMessages().getItems().size());
        assertTrue(employeePage.isGlobalMessagePresent("Password changed"));

        // return to original state
        employeePage.changePassword(EMPLOYEE_USER, EMPLOYEE_USER);
    }

    @Test
    @InSequence(3)
    public void employeeDetailsChange() {
        login(EMPLOYEE_USER, EMPLOYEE_USER);

        homePage.getToolbar().editProfile();

        employeePage.setEmployeeDetails("Jonathan", "Smith", null, null);
        employeePage.save();

        assertTrue(employeePage.isGlobalMessagePresent("All changes saved"));
        assertEquals("New employee name should be in the toolbar", "Jonathan Smith", employeePage.getToolbar().getEmployeeName());
        assertTrue("Should be home after Save on Employee page", getLocation().contains(PAGE_HOME));

        // reset
        homePage.getToolbar().editProfile();
        employeePage.setEmployeeDetails("John", "Employee", null, null);
        employeePage.save();

    }

    @Test
    @InSequence(4)
    public void employeesPageContainsInitialData() {
        login(ADMIN_USER, ADMIN_USER);

        homePage.getToolbar().goToEmployees();
        assertEquals(3, employeesPage.getEmployeesTable().getAllRows().size());

        EmployeesListRow employee = employeesPage.getEmployeesTable().getEmployeeByUsername(EMPLOYEE_USER);
        assertEquals(EMPLOYEE_NAME, employee.getName().getText());

        EmployeesListRow admin = employeesPage.getEmployeesTable().getEmployeeByUsername(ADMIN_USER);
        assertEquals(ADMIN_NAME, admin.getName().getText());

        EmployeesListRow accountant = employeesPage.getEmployeesTable().getEmployeeByUsername(ACCOUNTANT_USER);
        assertEquals(ACCOUNTANT_NAME, accountant.getName().getText());
    }

    @Test
    @InSequence(5)
    public void changeUserRole() {
        login(ADMIN_USER, ADMIN_USER);

        homePage.getToolbar().goToEmployees();
        EmployeesListRow employee = employeesPage.getEmployeesTable().getEmployeeByUsername(EMPLOYEE_USER);
        employee.editEmployee();
        assertTrue(getLocation().contains(PAGE_EMPLOYEE));

        employeePage.setUser(null, ROLE_EMPLOYEE, ROLE_ADMIN);
        employeePage.save();
        assertTrue(employeePage.isGlobalMessagePresent("All changes saved"));
        assertTrue(getLocation().contains(PAGE_EMPLOYEES));

        employeesPage.getToolbar().logout();

        loginPage.login(EMPLOYEE_USER, EMPLOYEE_USER);
        assertTrue(homePage.getToolbar().isEmployeesLinkPresent());

        // reset
        homePage.getToolbar().editProfile();
        employeePage.setUser(null, ROLE_EMPLOYEE);
        employeePage.save();
    }

    @Test
    @InSequence(6)
    public void addEmployee() {
        login(ADMIN_USER, ADMIN_USER);

        homePage.getToolbar().goToEmployees();

        employeesPage.addEmployee();
        assertTrue(getLocation().contains(PAGE_EMPLOYEE));

        employeePage.setPassword("password", "password");
        employeePage.setEmployeeDetails("Foo", "Bar", "123123123", "foo@bar.com");
        // try existing username
        employeePage.setUser("john", ROLE_EMPLOYEE);

        employeePage.save();
        assertTrue(employeePage.getUsernameMsg().advanced().isVisible());
        assertEquals("This username is not available", employeePage.getUsernameMsg().getDetail());

        employeePage.setUser("foo");
        employeePage.setPassword("password", "password");
        employeePage.save();
        assertTrue(employeesPage.isGlobalMessagePresent("Employee Foo Bar created"));
        assertEquals("foo@bar.com", employeesPage.getEmployeesTable().getEmployeeByUsername("foo").getEmail().getText());

        employeesPage.getToolbar().logout();

        loginPage.login("foo", "password");
        assertEquals("Foo Bar", employeePage.getToolbar().getEmployeeName());

    }

}
