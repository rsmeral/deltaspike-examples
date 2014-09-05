package org.jboss.examples.deltaspike.expensetracker.ftest.fragments;

import org.jboss.arquillian.graphene.findby.FindByJQuery;
import org.openqa.selenium.WebElement;
import org.richfaces.fragment.common.NullFragment;
import org.richfaces.fragment.dataTable.RichFacesDataTable;

public class ExpenseList extends RichFacesDataTable<NullFragment, ExpenseList.Row, ExpenseList.Footer> {

    public static class Row {

        @FindByJQuery("td[id$='date']")
        private WebElement date;

        @FindByJQuery("td[id$='purpose']")
        private WebElement purpose;

        @FindByJQuery("td[id$='receipt']")
        private WebElement receipt;

        @FindByJQuery("td[id$='value']")
        private WebElement value;

        @FindByJQuery("input[id$='edit']")
        private WebElement editBtn;

        @FindByJQuery("input[id$='delete']")
        private WebElement deleteBtn;

        public String getDate() {
            return date.getText();
        }

        public String getPurpose() {
            return purpose.getText();
        }

        public WebElement getReceipt() {
            return receipt;
        }

        public String getValue() {
            return value.getText();
        }

        public void edit() {
            editBtn.click();
        }

        public void delete() {
            deleteBtn.click();
        }

    }

    public static class Footer {

        @FindByJQuery("td[id$='total']")
        private WebElement total;

        public Long getTotal() {
            return Long.valueOf(total.getText());
        }

    }

}
