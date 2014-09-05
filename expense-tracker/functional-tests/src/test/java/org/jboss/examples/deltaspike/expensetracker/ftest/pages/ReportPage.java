package org.jboss.examples.deltaspike.expensetracker.ftest.pages;

import java.util.Date;
import org.jboss.arquillian.graphene.GrapheneElement;
import org.jboss.arquillian.graphene.page.Location;
import static org.jboss.examples.deltaspike.expensetracker.ftest.util.TestConstants.*;
import org.jboss.examples.deltaspike.expensetracker.ftest.fragments.ExpenseList;
import org.jboss.examples.deltaspike.expensetracker.ftest.fragments.ReimbursementList;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.richfaces.fragment.common.NullFragment;
import org.richfaces.fragment.dataTable.RichFacesDataTable;
import org.richfaces.fragment.inplaceInput.RichFacesInplaceInput;

@Location(PAGE_REPORT)
public class ReportPage extends TemplatePage {

    // Report form
    @FindBy(id = "reportForm:reportName")
    private RichFacesInplaceInput reportName;

    @FindBy(id = "reportForm:reportStatus")
    private WebElement reportStatus;

    @FindBy(id = "reportForm:reportAssignee")
    private WebElement reportAssignee;

    @FindBy(id = "reportForm:reportReporter")
    private WebElement reportReporter;

    @FindBy(id = "reportForm:reportDescription")
    private WebElement reportDescription;

    @FindBy(id = "reportForm:reportLastChangeDate")
    private WebElement reportLastChangeDate;

    @FindBy(id = "reportForm:reportLastChangeBy")
    private WebElement reportLastChangeBy;

    @FindBy(id = "reportForm:saveBtn")
    private WebElement saveBtn;

    @FindBy(id = "reportForm:cancelBtn")
    private WebElement cancelBtn;

    // Reimbursements
    @FindBy(id = "reimbursementsForm:reimbursementsTable")
    private ReimbursementList reimbursementsTable;

    @FindBy(id = "reimbursementsForm:addReimbursementBtn")
    private GrapheneElement addReimbursementBtn;

    // Expenses
    @FindBy(id = "expensesForm:expensesTable")
    private ExpenseList expensesTable;

    @FindBy(id = "expensesForm:addExpenseBtn")
    private GrapheneElement addExpenseBtn;

    // Report balance
    @FindBy(id = "reportBalance")
    private WebElement reportBalance;

    // Report actions
    @FindBy(id = "actionsForm:reopen")
    private GrapheneElement reopenBtn;

    @FindBy(id = "actionsForm:submit")
    private GrapheneElement submitBtn;

    @FindBy(id = "actionsForm:assignToMe")
    private GrapheneElement assignToMeBtn;

    @FindBy(id = "actionsForm:unassign")
    private GrapheneElement unassignBtn;

    @FindBy(id = "actionsForm:reject")
    private GrapheneElement rejectBtn;

    @FindBy(id = "actionsForm:approve")
    private GrapheneElement approveBtn;

    @FindBy(id = "actionsForm:settle")
    private GrapheneElement settleBtn;

    public String getReportName() {
        return reportName.getTextInput().getStringValue();
    }

    public String getReportStatus() {
        return reportStatus.getText();
    }

    public String getReportAssignee() {
        return reportAssignee.getText();
    }

    public String getReportReporter() {
        return reportReporter.getText();
    }

    public String getReportDescription() {
        return reportDescription.getText();
    }

    public String getReportLastChangeBy() {
        return reportLastChangeBy.getText();
    }

    public Date getReportLastChangeDate() {
        return new Date(Long.valueOf(reportLastChangeDate.getText()));
    }

    public void setReportName(String name) {
        reportName.getTextInput().clear();
        reportName.type(name).confirm();
    }

    public void setReportDescription(String desc) {
        reportDescription.clear();
        reportDescription.sendKeys(desc);
    }

    public RichFacesDataTable<NullFragment, ReimbursementList.Row, ReimbursementList.Footer> getReimbursementsTable() {
        return reimbursementsTable;
    }

    public RichFacesDataTable<NullFragment, ExpenseList.Row, ExpenseList.Footer> getExpensesTable() {
        return expensesTable;
    }

    public Long getReportBalance() {
        return Long.valueOf(reportBalance.getText());
    }

    public void save() {
        saveBtn.click();
    }

    public void cancel() {
        cancelBtn.click();
    }

    public void addReimbursement() {
        addReimbursementBtn.click();
    }

    public void addExpense() {
        addExpenseBtn.click();
    }

    public void reopen() {
        reopenBtn.click();
    }

    public void submit() {
        submitBtn.click();
    }

    public void assignToMe() {
        assignToMeBtn.click();
    }

    public void unassign() {
        unassignBtn.click();
    }

    public void reject() {
        rejectBtn.click();
    }

    public void approve() {
        approveBtn.click();
    }

    public void settle() {
        settleBtn.click();
    }

    public GrapheneElement getAddReimbursementBtn() {
        return addReimbursementBtn;
    }

    public GrapheneElement getAddExpenseBtn() {
        return addExpenseBtn;
    }

    public GrapheneElement getReopenBtn() {
        return reopenBtn;
    }

    public GrapheneElement getSubmitBtn() {
        return submitBtn;
    }

    public GrapheneElement getAssignToMeBtn() {
        return assignToMeBtn;
    }

    public GrapheneElement getUnassignBtn() {
        return unassignBtn;
    }

    public GrapheneElement getRejectBtn() {
        return rejectBtn;
    }

    public GrapheneElement getApproveBtn() {
        return approveBtn;
    }

    public GrapheneElement getSettleBtn() {
        return settleBtn;
    }
}
