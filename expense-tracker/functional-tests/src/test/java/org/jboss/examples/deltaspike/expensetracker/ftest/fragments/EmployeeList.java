package org.jboss.examples.deltaspike.expensetracker.ftest.fragments;

import org.jboss.arquillian.graphene.findby.FindByJQuery;
import org.jboss.examples.deltaspike.expensetracker.ftest.fragments.EmployeeList.Row;
import org.openqa.selenium.WebElement;
import org.richfaces.fragment.common.NullFragment;
import org.richfaces.fragment.dataTable.RichFacesDataTable;

public class EmployeeList extends RichFacesDataTable<NullFragment, Row, NullFragment> {

    public Row getEmployeeByUsername(String username) {
        if (advanced().isNoData()) {
            return null;
        }

        if (username == null) {
            return null;
        }

        for (Row row : getAllRows()) {
            if (username.equals(row.getUsername().getText())) {
                return row;
            }
        }

        return null;

    }

    public static class Row {

        @FindByJQuery(value = "td[id$='employeeUsername']")
        private WebElement username;

        @FindByJQuery(value = "td[id$='employeeName']")
        private WebElement name;

        @FindByJQuery(value = "td[id$='employeeEmail']")
        private WebElement email;

        @FindByJQuery(value = "td[id$='employeeBankAccount']")
        private WebElement bankAccount;

        @FindByJQuery(value = "input[id$='editEmployeeBtn']")
        private WebElement editEmployeeLink;

        public WebElement getUsername() {
            return username;
        }

        public WebElement getName() {
            return name;
        }

        public WebElement getEmail() {
            return email;
        }

        public WebElement getBankAccount() {
            return bankAccount;
        }

        public void editEmployee() {
            editEmployeeLink.click();
        }
    }

}
