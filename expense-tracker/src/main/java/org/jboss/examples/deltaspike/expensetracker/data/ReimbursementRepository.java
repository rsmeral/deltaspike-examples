package org.jboss.examples.deltaspike.expensetracker.data;

import java.util.Date;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.jboss.examples.deltaspike.expensetracker.domain.model.ExpenseReport;
import org.jboss.examples.deltaspike.expensetracker.domain.model.Reimbursement;

@ApplicationScoped
@Repository
public abstract class ReimbursementRepository implements EntityRepository<Reimbursement, Long> {

    public abstract List<Reimbursement> findByReport(ExpenseReport report);

    public abstract List<Reimbursement> findByDateBetween(Date from, Date to);

}
