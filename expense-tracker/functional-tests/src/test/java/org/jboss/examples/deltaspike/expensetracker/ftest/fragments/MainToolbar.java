package org.jboss.examples.deltaspike.expensetracker.ftest.fragments;

import org.jboss.arquillian.graphene.GrapheneElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MainToolbar {

    @FindBy(id = "toolbarForm:userMenu")
    private UserMenu userMenu;
    
    // because userMenu.advanced().getTopLevelElement().getText() doesn't work (.rf-ddm-lbl:eq(0) doesn't)
    @FindBy(id = "toolbarForm:userMenu_label")
    private WebElement userMenuLabel;
    
    @FindBy(id = "toolbarForm:navHome")
    private WebElement homeLink;

    @FindBy(id = "toolbarForm:navPurposes")
    private GrapheneElement purposesLink;

    @FindBy(id = "toolbarForm:navEmployees")
    private GrapheneElement employeesLink;

    public void goHome() {
        homeLink.click();
    }

    public void goToPurposes() {
        purposesLink.click();
    }

    public boolean isEmployeesLinkPresent() {
        return employeesLink.isPresent();
    }

    public boolean isPurposesLinkPresent() {
        return purposesLink.isPresent();
    }

    public void goToEmployees() {
        employeesLink.click();
    }

    public String getEmployeeName() {
        return userMenuLabel.getText();
    }

    public void editProfile() {
        userMenu.advanced().show();
        userMenu.selectItem("Edit profile");
    }

    public void logout() {
        userMenu.advanced().show();
        userMenu.selectItem("Logout");
    }

}
