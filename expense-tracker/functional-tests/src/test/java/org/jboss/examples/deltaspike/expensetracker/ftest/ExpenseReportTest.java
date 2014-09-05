package org.jboss.examples.deltaspike.expensetracker.ftest;

import org.jboss.examples.deltaspike.expensetracker.ftest.util.ExpenseTrackerFunctionalTestBase;
import java.text.MessageFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import org.jboss.arquillian.graphene.page.Page;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import static org.jboss.examples.deltaspike.expensetracker.ftest.util.TestConstants.*;
import org.jboss.examples.deltaspike.expensetracker.ftest.fragments.ReportList;
import org.jboss.examples.deltaspike.expensetracker.ftest.fragments.ReportListRow;
import org.jboss.examples.deltaspike.expensetracker.ftest.pages.ExpensePage;
import org.jboss.examples.deltaspike.expensetracker.ftest.pages.HomePage;
import org.jboss.examples.deltaspike.expensetracker.ftest.pages.LoginPage;
import org.jboss.examples.deltaspike.expensetracker.ftest.pages.ReimbursementPage;
import org.jboss.examples.deltaspike.expensetracker.ftest.pages.ReportPage;
import org.jboss.examples.deltaspike.expensetracker.ftest.pages.ReportsPage;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;

/*
 * Tests in this class are sequenced only because adding a report is an
 * irreversible operation in the application. 
 */
@RunWith(Arquillian.class)
public class ExpenseReportTest extends ExpenseTrackerFunctionalTestBase {

    private static final String NEW_REPORT_NAME = "Business Trip";
    private static final String NEW_REPORT_DESC = "Prague";
    private static final long NEW_REPORT_EXPENSES = 150l;

    @Page
    private HomePage homePage;

    @Page
    private ReportPage reportPage;

    @Page
    private ExpensePage expensePage;

    @Page
    private ReimbursementPage reimbursementPage;

    @Page
    private LoginPage loginPage;
    
    @Page
    private ReportsPage reportsPage;

    @Test
    @InSequence(1)
    public void initialDataPresent() {
        login(USER_EMPLOYEE, USER_EMPLOYEE);

        final String REPORT_NAME = INIT_REPORT_NAME;

        assertTrue(homePage.getReportedByCurrentUser().advanced().isVisible());
        assertFalse(homePage.getAssignedToCurrentAccountant().advanced().isVisible());
        assertFalse(homePage.getUnassigned().advanced().isVisible());

        assertEquals(1, homePage.getReportedByCurrentUser().getAllRows().size());
        assertReportInTable(homePage.getReportedByCurrentUser(), INIT_REPORT_NAME, INIT_REPORT_DESC, null, null, STATUS_SUBMITTED, null, NAME_ACCOUNTANT, INIT_REPORT_BALANCE);

    }

    @Test
    @InSequence(2)
    public void reportCreation() {
        login(USER_EMPLOYEE, USER_EMPLOYEE);

        // Save basic details
        homePage.createReport();

        reportPage.setReportName(NEW_REPORT_NAME);
        reportPage.setReportDescription(NEW_REPORT_DESC);
        reportPage.save();
        Date saveDate = new Date();

        assertTrue(getLocation().contains(PAGE_REPORT));
        assertTrue(reportPage.isGlobalMessagePresent(MSG_CHANGES_SAVED));
        assertTrue(reportPage.getAddExpenseBtn().isPresent());
        assertTrue(reportPage.getSubmitBtn().isPresent());
        assertEquals(NAME_EMPLOYEE, reportPage.getReportLastChangeBy());
        assertEquals(0, Double.valueOf(reportPage.getReportBalance()).intValue());

        reportPage.cancel();
        assertTrue(getLocation().contains(PAGE_HOME));
        assertEquals(2, homePage.getReportedByCurrentUser().getAllRows().size());
        assertReportInTable(homePage.getReportedByCurrentUser(), NEW_REPORT_NAME, NEW_REPORT_DESC, NAME_EMPLOYEE, saveDate, STATUS_OPEN, null, UNASSIGNED, 0l);

        // Add expenses
        homePage.getReportedByCurrentUser().getReportByName(NEW_REPORT_NAME).editReport();
        reportPage.addExpense();

        assertEquals(NEW_REPORT_NAME, expensePage.getReportView().getName());
        assertEquals(STATUS_OPEN, expensePage.getReportView().getStatus());
        assertEquals(REPORTED_BY + " " + NAME_EMPLOYEE, expensePage.getReportView().getReporter());
        assertEquals(UNASSIGNED, expensePage.getReportView().getAssignee());

        expensePage.setPurpose(PURPOSE_ACCOMODATION);
//        expensePage.setReceipt(null); TODO
        GregorianCalendar expenseDate1 = new GregorianCalendar() {
            {
                setTime(new Date());
                roll(GregorianCalendar.DATE, -2);
            }
        };

        expensePage.setDate(expenseDate1.getTime());
        expensePage.setValue(100);
        expensePage.save();

        assertTrue(reportPage.isGlobalMessagePresent(MSG_CHANGES_SAVED));
        assertTrue(getLocation().contains(PAGE_REPORT));

        reportPage.addExpense();
        expensePage.setPurpose(PURPOSE_FUEL);
//        expensePage.setReceipt(null); TODO
        GregorianCalendar expenseDate2 = new GregorianCalendar() {
            {
                setTime(new Date());
                roll(GregorianCalendar.DATE, -1);
            }
        };

        expensePage.setDate(expenseDate2.getTime());
        expensePage.setValue(50);
        expensePage.save();

        assertEquals(NEW_REPORT_EXPENSES, reportPage.getExpensesTable().getFooter().getTotal().longValue());
        assertEquals(-NEW_REPORT_EXPENSES, reportPage.getReportBalance().longValue());
    }

    @Test
    @InSequence(3)
    public void reportWorkflow() {
        login(USER_EMPLOYEE, USER_EMPLOYEE);

        homePage.getReportedByCurrentUser().getReportByName(NEW_REPORT_NAME).editReport();
        reportPage.submit();
        assertTrue(homePage.isGlobalMessagePresent(MessageFormat.format(MSG_REPORT_SUBMITTED, NEW_REPORT_NAME)));
        assertEquals(STATUS_SUBMITTED, homePage.getReportedByCurrentUser().getReportByName(NEW_REPORT_NAME).getStatus());

        homePage.getToolbar().logout();

        loginPage.login(USER_ACCOUNTANT, USER_ACCOUNTANT);

        assertTrue(homePage.getReportedByCurrentUser().advanced().isNoData());
        assertReportInTable(homePage.getAssignedToCurrentAccountant(), INIT_REPORT_NAME, INIT_REPORT_DESC, null, null, STATUS_SUBMITTED, NAME_EMPLOYEE, null, INIT_REPORT_BALANCE);
        assertReportInTable(homePage.getUnassigned(), NEW_REPORT_NAME, NEW_REPORT_DESC, NAME_EMPLOYEE, null, STATUS_SUBMITTED, NAME_EMPLOYEE, null, -NEW_REPORT_EXPENSES);

        homePage.getUnassigned().getReportByName(NEW_REPORT_NAME).editReport();
        reportPage.assignToMe();

        assertTrue(getLocation().contains(PAGE_REPORT));

        reportPage.addReimbursement();

        assertEquals(NEW_REPORT_NAME, reimbursementPage.getReportView().getName());
        assertEquals(STATUS_SUBMITTED, reimbursementPage.getReportView().getStatus());
        assertEquals(REPORTED_BY + " " + NAME_EMPLOYEE, reimbursementPage.getReportView().getReporter());
        assertEquals(ASSIGNED_TO + " " + NAME_ACCOUNTANT, reimbursementPage.getReportView().getAssignee());

        reimbursementPage.setDate(new GregorianCalendar() {
            {
                setTime(new Date());
                roll(GregorianCalendar.DATE, -2);
            }
        }.getTime());

        long REIMB = 130;
        reimbursementPage.setValue(REIMB);
        reimbursementPage.save();
        assertTrue(reportPage.isGlobalMessagePresent(MSG_CHANGES_SAVED));
        assertEquals(REIMB, reportPage.getReimbursementsTable().getFooter().getTotal().longValue());
        assertEquals(REIMB - NEW_REPORT_EXPENSES, reportPage.getReportBalance().longValue());

        reportPage.approve();
        assertTrue(homePage.isGlobalMessagePresent(MessageFormat.format(MSG_REPORT_APPROVED, NEW_REPORT_NAME)));

        homePage.getAssignedToCurrentAccountant().getReportByName(NEW_REPORT_NAME).editReport();

        reportPage.settle();
        assertTrue(homePage.isGlobalMessagePresent(MessageFormat.format(MSG_REPORT_SETTLED, NEW_REPORT_NAME)));

        // make sure the report that remains is the initial one
        assertEquals(1, homePage.getAssignedToCurrentAccountant().getAllRows().size());
        assertReportInTable(homePage.getAssignedToCurrentAccountant(), INIT_REPORT_NAME, INIT_REPORT_DESC, null, null, STATUS_SUBMITTED, NAME_EMPLOYEE, null, INIT_REPORT_BALANCE);
        
        homePage.showAllAssigned();
        assertEquals(2, reportsPage.getReportsTable().getAllRows().size());
        assertReportInTable(reportsPage.getReportsTable(), INIT_REPORT_NAME, INIT_REPORT_DESC, null, null, STATUS_SUBMITTED, NAME_EMPLOYEE, NAME_ACCOUNTANT, null);
        assertReportInTable(reportsPage.getReportsTable(), NEW_REPORT_NAME, NEW_REPORT_DESC, null, null, STATUS_SETTLED, NAME_EMPLOYEE, NAME_ACCOUNTANT, null);
    }

    private static void assertReportInTable(ReportList table, String name, String desc, String lastChangedBy, Date lastChangeDate, String status, String reporter, String assignee, Long balance) {
        if (name == null) {
            fail("Can't assert null report");
        }

        ReportListRow report = table.getReportByName(name);

        if (desc != null) {
            assertEquals(desc, report.getDesc());
        }

        if (lastChangedBy != null) {
            assertEquals(lastChangedBy, report.getLastChangeBy());
        }

        if (lastChangeDate != null) {
            assertTrue(Math.abs(lastChangeDate.getTime() - report.getLastChangeDate().getTime()) < DATE_DIFFERENCE_TOLERANCE);
        }

        if (status != null) {
            assertEquals(status, report.getStatus());
        }

        if (reporter != null) {
            assertEquals(reporter, report.getReporter());
        }

        if (assignee != null) {
            assertEquals(assignee, report.getAssignee());
        }

        if (balance != null) {
            assertEquals(balance.longValue(), Long.parseLong(report.getBalance()));
        }
    }

}
