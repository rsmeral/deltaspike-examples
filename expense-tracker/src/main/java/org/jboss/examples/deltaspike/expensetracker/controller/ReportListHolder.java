package org.jboss.examples.deltaspike.expensetracker.controller;

import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import org.apache.deltaspike.data.api.QueryResult;
import org.jboss.examples.deltaspike.expensetracker.domain.model.ExpenseReport;

/**
 * Ugliness. TODO: Needs a better solution.
 */
@Named
@SessionScoped
public class ReportListHolder implements Serializable {

    private QueryResult<List<ExpenseReport>> query;

    private String listTitle;

    public String getListTitle() {
        return listTitle;
    }

    public void setListTitle(String listTitle) {
        this.listTitle = listTitle;
    }

    public QueryResult<List<ExpenseReport>> getQuery() {
        return query;
    }

    public void setQuery(QueryResult<List<ExpenseReport>> query) {
        this.query = query;
    }
}
