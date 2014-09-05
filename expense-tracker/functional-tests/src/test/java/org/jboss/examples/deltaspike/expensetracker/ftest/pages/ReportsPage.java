package org.jboss.examples.deltaspike.expensetracker.ftest.pages;

import org.jboss.arquillian.graphene.page.Location;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import static org.jboss.examples.deltaspike.expensetracker.ftest.util.TestConstants.*;
import org.jboss.examples.deltaspike.expensetracker.ftest.fragments.ReportList;

@Location(PAGE_REPORTS)
public class ReportsPage extends TemplatePage {

    @FindBy(id = "reportsForm")
    private ReportList reportList;

    @FindBy(id = "reportsForm:createReportBtn")
    private WebElement createReportBtn;

    public void createReport() {
        createReportBtn.click();
    }

    public ReportList getReportsTable() {
        return reportList;
    }
    
}
