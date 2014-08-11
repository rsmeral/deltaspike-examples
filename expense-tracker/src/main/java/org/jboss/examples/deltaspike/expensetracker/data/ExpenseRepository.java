package org.jboss.examples.deltaspike.expensetracker.data;

import java.util.Date;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.criteria.CriteriaSupport;
import org.jboss.examples.deltaspike.expensetracker.model.Employee;
import org.jboss.examples.deltaspike.expensetracker.model.Expense;
import org.jboss.examples.deltaspike.expensetracker.model.ExpenseReport;
import org.jboss.examples.deltaspike.expensetracker.model.ExpenseReport_;
import org.jboss.examples.deltaspike.expensetracker.model.Expense_;

@ApplicationScoped
@Repository
public abstract class ExpenseRepository implements EntityRepository<Expense, Long>, CriteriaSupport<Expense> {

    public abstract List<Expense> findByReport(ExpenseReport report);

    public abstract List<Expense> findByDateBetween(Date from, Date to);

    public List<Expense> findByEmployee(Employee employee) {
        return criteria()
                .join(Expense_.report,
                        where(ExpenseReport.class)
                        .eq(ExpenseReport_.reporter, employee)
                ).getResultList();

    }

}
