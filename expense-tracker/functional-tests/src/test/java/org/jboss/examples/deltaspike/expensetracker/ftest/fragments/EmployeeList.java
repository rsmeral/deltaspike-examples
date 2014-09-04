package org.jboss.examples.deltaspike.expensetracker.ftest.fragments;

import org.richfaces.fragment.common.NullFragment;
import org.richfaces.fragment.dataTable.RichFacesDataTable;

public class EmployeeList extends RichFacesDataTable<NullFragment, EmployeesListRow, NullFragment> {

    public EmployeesListRow getEmployeeByUsername(String username) {
        if (advanced().isNoData()) {
            return null;
        }

        if (username == null) {
            return null;
        }

        for (EmployeesListRow row : getAllRows()) {
            if (username.equals(row.getUsername().getText())) {
                return row;
            }
        }

        return null;

    }

}
