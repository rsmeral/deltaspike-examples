package org.jboss.examples.deltaspike.expensetracker.ftest.fragments;

import org.jboss.arquillian.graphene.findby.FindByJQuery;
import org.openqa.selenium.WebElement;

public class ReportView {

    @FindByJQuery("[id$='name']")
    private WebElement name;

    @FindByJQuery("[id$='status']")
    private WebElement status;

    @FindByJQuery("[id$='reporter']")
    private WebElement reporter;

    @FindByJQuery("[id$='assignee']")
    private WebElement assignee;

    public String getName() {
        return name.getText();
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

}
