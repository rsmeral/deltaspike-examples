package org.jboss.examples.deltaspike.expensetracker.ftest.fragments;

import java.util.Date;
import org.jboss.arquillian.graphene.findby.FindByJQuery;
import org.openqa.selenium.WebElement;

public class ReportListRow {

    @FindByJQuery("td[id$='reportId']")
    private WebElement id;

    @FindByJQuery("td[id$='reportName'] > a")
    private WebElement nameLink;

    @FindByJQuery("td[id$='reportDesc']")
    private WebElement desc;

    @FindByJQuery("td[id$='reportLastChange'] > span[id$='date']")
    private WebElement lastChangeDate;
    
    @FindByJQuery("td[id$='reportLastChange'] > span[id$='by']")
    private WebElement lastChangeBy;

    @FindByJQuery("td[id$='reportStatus']")
    private WebElement status;

    @FindByJQuery("td[id$='reportReporter']")
    private WebElement reporter;

    @FindByJQuery("td[id$='reportAssignee']")
    private WebElement assignee;

    @FindByJQuery("td[id$='reportBalance']")
    private WebElement balance;

    public WebElement getId() {
        return id;
    }

    public String getName() {
        return nameLink.getText();
    }

    public String getDesc() {
        return desc.getText();
    }

    public String getLastChangeBy() {
        return lastChangeBy.getText();
    }

    public Date getLastChangeDate() {
        return new Date(Long.valueOf(lastChangeDate.getText()));
    }

    public String getStatus() {
        return status.getText();
    }

    public String getReporter() {
        return reporter.getText();
    }

    public String getAssignee() {
        return assignee.getText();
    }

    public String getBalance() {
        return balance.getText();
    }

    public void editReport() {
        nameLink.click();
    }

}
