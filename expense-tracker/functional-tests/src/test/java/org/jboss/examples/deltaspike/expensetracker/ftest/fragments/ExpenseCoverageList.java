package org.jboss.examples.deltaspike.expensetracker.ftest.fragments;

import org.jboss.arquillian.graphene.findby.FindByJQuery;
import org.jboss.examples.deltaspike.expensetracker.ftest.fragments.ExpenseCoverageList.Row;
import org.openqa.selenium.WebElement;
import org.richfaces.fragment.common.NullFragment;
import org.richfaces.fragment.dataTable.RichFacesDataTable;

public class ExpenseCoverageList extends RichFacesDataTable<NullFragment, Row, NullFragment> {

    public static class Row {

        @FindByJQuery("input[id$='covered']")
        private WebElement covered;

        @FindByJQuery("span[id$='expenseDate']")
        private WebElement date;

        @FindByJQuery("span[id$='expensePurpose']")
        private WebElement purpose;

        @FindByJQuery("span[id$='expenseValue']")
        private WebElement value;

        public String getDate() {
            return date.getText();
        }

        public String getPurpose() {
            return purpose.getText();
        }

        public String getValue() {
            return value.getText();
        }

        public void select() {
            if (!covered.isSelected()) {
                covered.click();
            }
        }

        public void deselect() {
            if (covered.isSelected()) {
                covered.click();
            }
        }

        public boolean isSelected() {
            return covered.isSelected();
        }

    }

}
