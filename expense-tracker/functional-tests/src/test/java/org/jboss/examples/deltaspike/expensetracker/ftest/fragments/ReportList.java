package org.jboss.examples.deltaspike.expensetracker.ftest.fragments;

import org.richfaces.fragment.common.NullFragment;
import org.richfaces.fragment.dataTable.RichFacesDataTable;

public class ReportList extends RichFacesDataTable<NullFragment, ReportListRow, NullFragment> {

    public ReportListRow getReportByName(String name) {
        if (advanced().isNoData()) {
            return null;
        }

        if (name == null) {
            return null;
        }

        for (ReportListRow row : getAllRows()) {
            if (name.equals(row.getName())) {
                return row;
            }
        }

        return null;

    }

}
