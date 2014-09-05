package org.jboss.examples.deltaspike.expensetracker.ftest.pages;

import java.util.Date;
import org.jboss.arquillian.graphene.findby.FindByJQuery;
import org.jboss.arquillian.graphene.page.Location;
import static org.jboss.examples.deltaspike.expensetracker.ftest.util.TestConstants.*;
import org.jboss.examples.deltaspike.expensetracker.ftest.fragments.ReportView;
import org.joda.time.DateTime;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import org.richfaces.fragment.calendar.RichFacesCalendar;
import org.richfaces.fragment.message.RichFacesMessage;

@Location(PAGE_EXPENSE)
public class ExpensePage extends TemplatePage {

    @FindBy(id = "selectedReport:panel")
    private ReportView reportView;

    @FindByJQuery("[id$='purpose']")
    private WebElement purpose;

    @FindByJQuery("[id$='purposeMessage']")
    private RichFacesMessage purposeMsg;
    
    @FindByJQuery("[id$='receipt']")
    private WebElement receipt;

    @FindByJQuery("[id$='receiptMessage']")
    private RichFacesMessage receiptMsg;
    
    @FindByJQuery("[id$='date']")
    private RichFacesCalendar date;

    @FindByJQuery("[id$='dateMessage']")
    private RichFacesMessage dateMsg;
    
    @FindByJQuery("[id$='value']")
    private WebElement value;

    @FindByJQuery("[id$='valueMessage']")
    private RichFacesMessage valueMsg;
    
    @FindByJQuery("[id$='addReceiptBtn']")
    private WebElement addReceiptBtn;
    
    @FindByJQuery("[id$='saveBtn']")
    private WebElement saveBtn;
    
    @FindByJQuery("[id$='cancelBtn']")
    private WebElement cancelBtn;

    public ReportView getReportView() {
        return reportView;
    }

    public String getPurpose() {
        return new Select(purpose).getFirstSelectedOption().getText();
    }

    public RichFacesMessage getPurposeMsg() {
        return purposeMsg;
    }

    public String getReceipt() {
        return new Select(receipt).getFirstSelectedOption().getText();
    }

    public RichFacesMessage getReceiptMsg() {
        return receiptMsg;
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

    public void setPurpose(String purpose) {
        new Select(this.purpose).selectByVisibleText(purpose);
    }

    public void setReceipt(String receipt) {
        new Select(this.receipt).selectByVisibleText(receipt);
    }

    public void setDate(Date date) {
        this.date.setDate(new DateTime(date.getTime()));
    }

    public void setValue(Integer value) {
        this.value.clear();
        this.value.sendKeys(value.toString());
    }
    
    public void save() {
        saveBtn.click();
    }
    
    public void cancel() {
        cancelBtn.click();
    }
    
    public void addReceipt() {
        addReceiptBtn.click();
    }
    
}
