package org.jboss.examples.deltaspike.expensetracker.ftest.pages;

import org.jboss.arquillian.graphene.page.Location;
import org.jboss.examples.deltaspike.expensetracker.ftest.fragments.EmployeeList;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import static org.jboss.examples.deltaspike.expensetracker.ftest.TestConstants.*;

@Location(PAGE_EMPLOYEES)
public class EmployeesPage extends TemplatePage {

    @FindBy(id = "employeesForm:employeesTable")
    private EmployeeList employeesTable;

    @FindBy(id = "employeesForm:addEmployeeBtn")
    private WebElement addEmployeeLink;

    public void addEmployee() {
        addEmployeeLink.click();
    }

    public EmployeeList getEmployeesTable() {
        return employeesTable;
    }
    
}
