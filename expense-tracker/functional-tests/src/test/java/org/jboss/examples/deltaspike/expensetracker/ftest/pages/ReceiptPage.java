package org.jboss.examples.deltaspike.expensetracker.ftest.pages;

import org.jboss.arquillian.graphene.findby.FindByJQuery;
import org.jboss.arquillian.graphene.page.Location;
import org.jboss.examples.deltaspike.expensetracker.ftest.fragments.ExpenseCoverageList;
import org.jboss.examples.deltaspike.expensetracker.ftest.fragments.ReportView;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.richfaces.fragment.fileUpload.RichFacesFileUpload;
import org.richfaces.fragment.message.RichFacesMessage;

import static org.jboss.examples.deltaspike.expensetracker.ftest.util.TestConstants.*;

@Location(PAGE_RECEIPT)
public class ReceiptPage extends TemplatePage {

    @FindBy(id = "selectedReport:panel")
    private ReportView reportView;

    @FindBy(id = "receiptForm:file")
    private RichFacesFileUpload fileUpload;

    @FindByJQuery("[id$='fileMessage']")
    private RichFacesMessage fileUploadMsg;

    @FindBy(id = "receiptForm:nameNew")
    private WebElement nameInput;

    @FindBy(id = "receiptForm:nameStored")
    private WebElement nameOutput;

    @FindByJQuery("[id$='nameMessage']")
    private RichFacesMessage nameMsg;

    @FindBy(id = "receiptForm:importedBy")
    private WebElement importedBy;

    @FindBy(id = "receiptForm:importDate")
    private WebElement importDate;

    @FindBy(id = "receiptForm:saveBtn")
    private WebElement saveBtn;

    @FindBy(id = "receiptForm:cancelBtn")
    private WebElement cancelBtn;

    @FindBy(id = "receiptForm:backBtn")
    private WebElement backBtn;

    @FindBy(id = "receiptForm:imageNew")
    private WebElement imageNew;

    @FindBy(id = "receiptForm:imageStored")
    private WebElement imageStored;

    @FindBy(id = "receiptForm:coveredExpensesTable")
    private ExpenseCoverageList expenseCoverageList;

    @FindBy(id = "receiptForm:updateCoverageBtn")
    private WebElement updateCoverageBtn;

    public ReportView getReportView() {
        return reportView;
    }

    public RichFacesFileUpload getFileUpload() {
        return fileUpload;
    }

    public RichFacesMessage getFileUploadMsg() {
        return fileUploadMsg;
    }

    public RichFacesMessage getNameMsg() {
        return nameMsg;
    }

    public WebElement getImageNew() {
        return imageNew;
    }

    public WebElement getImageStored() {
        return imageStored;
    }

    public ExpenseCoverageList getExpenseCoverageList() {
        return expenseCoverageList;
    }

    public String getName() {
        return nameOutput.getText();
    }

    public String getImportedBy() {
        return importedBy.getText();
    }

    public String getImportDate() {
        return importDate.getText();
    }

    public void setName(String name) {
        this.nameInput.clear();
        this.nameInput.sendKeys(name);
    }

    public void cancel() {
        cancelBtn.click();
    }

    public void back() {
        backBtn.click();
    }

    public void save() {
        saveBtn.click();
    }

    public void updateCoverage() {
        updateCoverageBtn.click();
    }

}
