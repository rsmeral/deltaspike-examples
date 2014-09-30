package org.jboss.examples.deltaspike.expensetracker.data;

import java.util.Date;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import org.apache.deltaspike.data.api.*;
import org.apache.deltaspike.data.api.criteria.CriteriaSupport;
import org.jboss.examples.deltaspike.expensetracker.data.resources.MainEMResolver;
import org.jboss.examples.deltaspike.expensetracker.domain.model.*;

@ApplicationScoped
@Repository
@EntityManagerConfig(entityManagerResolver = MainEMResolver.class)
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

    public abstract List<Expense> findByReceipt(Receipt receipt);

    @Query("select e from Expense e where report=:report and (receipt=:receipt or receipt=null) order by receipt")
    public abstract List<Expense> findByReportAndOptionalReceipt(@QueryParam("report") ExpenseReport report, @QueryParam("receipt") Receipt receipt);


}
