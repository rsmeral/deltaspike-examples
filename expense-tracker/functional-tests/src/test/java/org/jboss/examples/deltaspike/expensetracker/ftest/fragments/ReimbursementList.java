package org.jboss.examples.deltaspike.expensetracker.ftest.fragments;

import org.jboss.arquillian.graphene.findby.FindByJQuery;
import org.openqa.selenium.WebElement;
import org.richfaces.fragment.common.NullFragment;
import org.richfaces.fragment.dataTable.RichFacesDataTable;

public class ReimbursementList extends RichFacesDataTable<NullFragment, ReimbursementList.Row, ReimbursementList.Footer> {

    public static class Row {

        @FindByJQuery("td[id$='date']")
        private WebElement date;

        @FindByJQuery("td[id$='creator']")
        private WebElement creator;

        @FindByJQuery("td[id$='value']")
        private WebElement value;

        @FindByJQuery("input[id$='edit']")
        private WebElement editBtn;

        @FindByJQuery("input[id$='delete']")
        private WebElement deleteBtn;

        public String getDate() {
            return date.getText();
        }

        public String getCreator() {
            return creator.getText();
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
