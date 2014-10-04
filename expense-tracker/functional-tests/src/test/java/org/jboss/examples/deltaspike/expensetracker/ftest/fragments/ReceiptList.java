package org.jboss.examples.deltaspike.expensetracker.ftest.fragments;

import org.jboss.arquillian.graphene.findby.FindByJQuery;
import org.jboss.examples.deltaspike.expensetracker.ftest.fragments.ReceiptList.Row;
import org.openqa.selenium.WebElement;
import org.richfaces.fragment.common.NullFragment;
import org.richfaces.fragment.dataTable.RichFacesDataTable;

public class ReceiptList extends RichFacesDataTable<NullFragment, Row, NullFragment> {

    public static class Row {

        @FindByJQuery("td[id$='name']")
        private WebElement name;

        @FindByJQuery("td[id$='importDate']")
        private WebElement importDate;

        @FindByJQuery("td[id$='importedBy']")
        private WebElement importedBy;

        @FindByJQuery("input[id$='show']")
        private WebElement showBtn;

        @FindByJQuery("input[id$='delete']")
        private WebElement deleteBtn;

        public String getName() {
            return name.getText();
        }

        public String getImportDate() {
            return importDate.getText();
        }

        public String getImportedBy() {
            return importedBy.getText();
        }

        public void show() {
            showBtn.click();
        }

        public void delete() {
            deleteBtn.click();
        }

    }

}
