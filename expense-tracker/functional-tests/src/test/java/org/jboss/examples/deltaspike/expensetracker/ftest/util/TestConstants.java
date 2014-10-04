package org.jboss.examples.deltaspike.expensetracker.ftest.util;

public class TestConstants {

    public static final long DATE_DIFFERENCE_TOLERANCE = 5 * 60 * 1000;// 2 minutes

    public static final String USER_EMPLOYEE = "john";
    public static final String NAME_EMPLOYEE_FIRST = "John";
    public static final String NAME_EMPLOYEE_LAST = "Employee";
    public static final String NAME_EMPLOYEE = NAME_EMPLOYEE_FIRST + " " + NAME_EMPLOYEE_LAST;

    public static final String INIT_REPORT_NAME = "GeeCon 2013";
    public static final String INIT_REPORT_DESC = "Krakow, 3 days";
    public static final Long INIT_REPORT_BALANCE = -110l;

    public static final String INIT_RECEIPT_NAME = "Hotel X invoice";

    public static final String USER_ACCOUNTANT = "anna";
    public static final String NAME_ACCOUNTANT = "Anna Accountant";

    public static final String USER_ADMIN = "admin";
    public static final String NAME_ADMIN = "Admin Administrator";

    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ROLE_EMPLOYEE = "EMPLOYEE";
    public static final String ROLE_ACCOUNTANT = "ACCOUNTANT";

    public static final String PAGE_HOME = "secured/home.jsf";
    public static final String PAGE_LOGIN = "login.jsf";
    public static final String PAGE_ERROR = "error.jsf";

    public static final String PAGE_REPORT = "secured/report.jsf";
    public static final String PAGE_REPORTS = "secured/reports.jsf";
    public static final String PAGE_EXPENSE = "secured/expense.jsf";
    public static final String PAGE_REIMBURSEMENT = "secured/reimbursement.jsf";
    public static final String PAGE_EMPLOYEE = "secured/employee.jsf";
    public static final String PAGE_EMPLOYEES = "secured/admin/employees.jsf";
    public static final String PAGE_RECEIPT = "secured/receipt.jsf";
    public static final String PAGE_RECEIPTS = "secured/receipts.jsf";
    public static final String PAGE_PURPOSE = "secured/purpose.jsf";
    public static final String PAGE_PURPOSES = "secured/admin/purposes.jsf";

    public static final String MSG_CHANGES_SAVED = "All changes saved";
    public static final String MSG_PWD_DONT_MATCH = "Passwords don't match";
    public static final String MSG_PWD_CHANGED = "Password changed";
    public static final String MSG_USERNAME_TAKEN = "This username is not available";
    public static final String MSG_EMPLOYEE_CREATED = "Employee ''{0} {1}'' created";
    public static final String MSG_NO_AUTH_ROLE = "None of the user's roles are authorized to access this resource";
    public static final String MSG_BAD_LOGIN = "Bad username or password";
    public static final String MSG_REPORT_SUBMITTED = "Report ''{0}'' submitted";
    public static final String MSG_REPORT_APPROVED = "Report ''{0}'' approved";
    public static final String MSG_REPORT_SETTLED = "Report ''{0}'' settled";

    public static final String STATUS_SUBMITTED = "SUBMITTED";
    public static final String STATUS_OPEN = "OPEN";
    public static final String STATUS_SETTLED = "SETTLED";

    public static final String PURPOSE_ACCOMODATION = "Accommodation";
    public static final String PURPOSE_FUEL = "Fuel";

    public static final String UNASSIGNED = "Unassigned";
    public static final String REPORTED_BY = "Reported by";
    public static final String ASSIGNED_TO = "Assigned to";

}
