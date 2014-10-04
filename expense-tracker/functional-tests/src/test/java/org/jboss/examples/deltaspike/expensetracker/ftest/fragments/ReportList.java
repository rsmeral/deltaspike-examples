package org.jboss.examples.deltaspike.expensetracker.ftest.fragments;

import java.util.Date;
import org.jboss.arquillian.graphene.findby.FindByJQuery;
import org.jboss.examples.deltaspike.expensetracker.ftest.fragments.ReportList.Row;
import org.openqa.selenium.WebElement;
import org.richfaces.fragment.common.NullFragment;
import org.richfaces.fragment.dataTable.RichFacesDataTable;

public class ReportList extends RichFacesDataTable<NullFragment, Row, NullFragment> {

    public Row getReportByName(String name) {
        if (advanced().isNoData()) {
            return null;
        }

        if (name == null) {
            return null;
        }

        for (Row row : getAllRows()) {
            if (name.equals(row.getName())) {
                return row;
            }
        }

        return null;

    }

    public static class Row {

        @FindByJQuery(value = "td[id$='reportId']")
        private WebElement id;

        @FindByJQuery(value = "td[id$='reportName'] > a")
        private WebElement nameLink;

        @FindByJQuery(value = "td[id$='reportDesc']")
        private WebElement desc;

        @FindByJQuery(value = "td[id$='reportLastChange'] > span[id$='date']")
        private WebElement lastChangeDate;

        @FindByJQuery(value = "td[id$='reportLastChange'] > span[id$='by']")
        private WebElement lastChangeBy;

        @FindByJQuery(value = "td[id$='reportStatus']")
        private WebElement status;

        @FindByJQuery(value = "td[id$='reportReporter']")
        private WebElement reporter;

        @FindByJQuery(value = "td[id$='reportAssignee']")
        private WebElement assignee;

        @FindByJQuery(value = "td[id$='reportBalance']")
        private WebElement balance;

        public WebElement getId() {
            return id;
        }

        public String getName() {
            return nameLink.getText();
        }

        public String getDesc() {
            return desc.getText();
        }

        public String getLastChangeBy() {
            return lastChangeBy.getText();
        }

        public Date getLastChangeDate() {
            return new Date(Long.valueOf(lastChangeDate.getText()));
        }

        public String getStatus() {
            return status.getText();
        }

        public String getReporter() {
            return reporter.getText();
        }

        public String getAssignee() {
            return assignee.getText();
        }

        public String getBalance() {
            return balance.getText();
        }

        public void editReport() {
            nameLink.click();
        }
    }

}
