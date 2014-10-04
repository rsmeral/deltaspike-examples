package org.jboss.examples.deltaspike.expensetracker.ftest;

import org.jboss.arquillian.graphene.page.Page;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.examples.deltaspike.expensetracker.ftest.fragments.ExpenseCoverageList;
import org.jboss.examples.deltaspike.expensetracker.ftest.fragments.ExpenseList;
import org.jboss.examples.deltaspike.expensetracker.ftest.fragments.ReceiptList;
import org.jboss.examples.deltaspike.expensetracker.ftest.pages.HomePage;
import org.jboss.examples.deltaspike.expensetracker.ftest.pages.ReceiptPage;
import org.jboss.examples.deltaspike.expensetracker.ftest.pages.ReportPage;
import org.jboss.examples.deltaspike.expensetracker.ftest.util.ExpenseTrackerFunctionalTestBase;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.jboss.examples.deltaspike.expensetracker.ftest.util.TestConstants.*;
import static org.junit.Assert.assertEquals;

@RunWith(Arquillian.class)
public class ReceiptTest extends ExpenseTrackerFunctionalTestBase {

    @Page
    private HomePage homePage;

    @Page
    private ReportPage reportPage;

    @Page
    private ReceiptPage receiptPage;

    @Test
    @InSequence(1)
    public void assertInitialData() {
        login(USER_EMPLOYEE, USER_EMPLOYEE);

        homePage.getReportedByCurrentUser().getReportByName(INIT_REPORT_NAME).editReport();

        ReceiptList receipts = reportPage.getReceiptsTable();
        assertEquals(1, receipts.getAllRows().size());

        ReceiptList.Row theReceipt = receipts.getFirstRow();
        assertEquals(INIT_RECEIPT_NAME, theReceipt.getName());

        for (ExpenseList.Row expense : reportPage.getExpensesTable().getAllRows()) {
            if (expense.getPurpose().equals(PURPOSE_ACCOMODATION)) {
                assertEquals(INIT_RECEIPT_NAME, expense.getReceipt().getText());
            }
        }

        theReceipt.show();

        assertEquals(NAME_EMPLOYEE, receiptPage.getImportedBy());
        assertEquals(INIT_RECEIPT_NAME, receiptPage.getName());
        ExpenseCoverageList expenseCoverageList = receiptPage.getExpenseCoverageList();
        for (ExpenseCoverageList.Row row : expenseCoverageList.getAllRows()) {
            assertEquals(row.isSelected(), row.getPurpose().equals(PURPOSE_ACCOMODATION));
        }
    }

}
