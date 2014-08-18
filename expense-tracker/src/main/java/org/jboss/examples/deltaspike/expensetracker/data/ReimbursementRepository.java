package org.jboss.examples.deltaspike.expensetracker.data;

import java.util.Date;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.jboss.examples.deltaspike.expensetracker.model.ExpenseReport;
import org.jboss.examples.deltaspike.expensetracker.model.Reimbursement;

@ApplicationScoped
@Repository
@Named
public abstract class ReimbursementRepository implements EntityRepository<Reimbursement, Long> {

    public abstract List<Reimbursement> findByReport(ExpenseReport report);
    
    public abstract List<Reimbursement> findByDateBetween(Date from, Date to);
        
}
