package org.jboss.examples.deltaspike.expensetracker.ftest.pages;

import java.util.Date;
import org.jboss.arquillian.graphene.findby.FindByJQuery;
import org.jboss.arquillian.graphene.page.Location;
import static org.jboss.examples.deltaspike.expensetracker.ftest.util.TestConstants.*;
import org.jboss.examples.deltaspike.expensetracker.ftest.fragments.ReportView;
import org.joda.time.DateTime;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.richfaces.fragment.calendar.RichFacesCalendar;
import org.richfaces.fragment.message.RichFacesMessage;

@Location(PAGE_REIMBURSEMENT)
public class ReimbursementPage extends TemplatePage {

    @FindBy(id = "selectedReport:panel")
    private ReportView reportView;
    
    @FindByJQuery("[id$='date']")
    private RichFacesCalendar date;

    @FindByJQuery("[id$='dateMessage']")
    private RichFacesMessage dateMsg;
    
    @FindByJQuery("[id$='value']")
    private WebElement value;

    @FindByJQuery("[id$='valueMessage']")
    private RichFacesMessage valueMsg;
    
    @FindByJQuery("[id$='saveBtn']")
    private WebElement saveBtn;
    
    @FindByJQuery("[id$='cancelBtn']")
    private WebElement cancelBtn;

    public ReportView getReportView() {
        return reportView;
    }

    public Date getDate() {
        return date.getDate().toDate();
    }

    public RichFacesMessage getDateMsg() {
        return dateMsg;
    }

    public WebElement getValue() {
        return value;
    }

    public RichFacesMessage getValueMsg() {
        return valueMsg;
    }

    public void setDate(Date date) {
        this.date.setDate(new DateTime(date.getTime()));
    }

    public void setValue(Long value) {
        this.value.clear();
        this.value.sendKeys(value.toString());
    }
    
    public void save() {
        saveBtn.click();
    }
    
    public void cancel() {
        cancelBtn.click();
    }
    
}
