package org.jboss.examples.deltaspike.expensetracker.ftest.pages;

import org.jboss.arquillian.graphene.page.Location;
import org.jboss.examples.deltaspike.expensetracker.ftest.fragments.ReportList;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import static org.jboss.examples.deltaspike.expensetracker.ftest.TestConstants.*;

@Location(PAGE_HOME)
public class HomePage extends TemplatePage {

    @FindBy(id = "reportedByMeForm")
    private ReportList reportedByCurrentUser;

    @FindBy(id = "assignedToMeForm")
    private ReportList assignedToCurrentAccountant;

    @FindBy(id = "unassignedForm")
    private ReportList unassigned;

    @FindBy(id = "reportedByMeForm:createReportBtn")
    private WebElement createReportButton;

    @FindBy(id = "reportedByMeForm:showAllBtn")
    private WebElement showAllReportedButton;

    @FindBy(id = "assignedToMeForm:showAllBtn")
    private WebElement showAllAssignedButton;

    public void goHome() {
        getToolbar().goHome();
    }

    public void goToPurposes() {
        getToolbar().goToPurposes();
    }

    public void goToEmployees() {
        getToolbar().goToEmployees();
    }

    public void logout() {
        getToolbar().logout();
    }

    public void editProfile() {
        getToolbar().editProfile();
    }

    public void createReport() {
        createReportButton.click();
    }

    public void showAllReported() {
        showAllReportedButton.click();
    }

    public void showAllAssigned() {
        showAllAssignedButton.click();
    }

    public ReportList getReportedByCurrentUser() {
        return reportedByCurrentUser;
    }

    public ReportList getAssignedToCurrentAccountant() {
        return assignedToCurrentAccountant;
    }

    public ReportList getUnassigned() {
        return unassigned;
    }

}
