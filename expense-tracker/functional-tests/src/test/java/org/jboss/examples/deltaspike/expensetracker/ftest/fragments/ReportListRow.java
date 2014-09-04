package org.jboss.examples.deltaspike.expensetracker.ftest.fragments;

import org.jboss.arquillian.graphene.findby.FindByJQuery;
import org.openqa.selenium.WebElement;

public class ReportListRow {

    @FindByJQuery("td[id$='reportId']")
    private WebElement id;

    @FindByJQuery("td[id$='reportName'] > a")
    private WebElement nameLink;

    @FindByJQuery("td[id$='reportDesc']")
    private WebElement desc;

    @FindByJQuery("td[id$='reportLastChange']")
    private WebElement lastChange;

    @FindByJQuery("td[id$='reportStatus']")
    private WebElement status;

    @FindByJQuery("td[id$='reportReporter']")
    private WebElement reporter;

    @FindByJQuery("td[id$='reportAssignee']")
    private WebElement assignee;

    @FindByJQuery("td[id$='reportId']")
    private WebElement balance;

    public WebElement getId() {
        return id;
    }

    public String getName() {
        return nameLink.getText();
    }

    public WebElement getDesc() {
        return desc;
    }

    public WebElement getLastChange() {
        return lastChange;
    }

    public WebElement getStatus() {
        return status;
    }

    public WebElement getReporter() {
        return reporter;
    }

    public WebElement getAssignee() {
        return assignee;
    }

    public WebElement getBalance() {
        return balance;
    }

    public void editReport() {
        nameLink.click();
    }

}
