package org.jboss.examples.deltaspike.expensetracker.data;

import java.util.Date;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import org.apache.deltaspike.data.api.EntityManagerConfig;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.jboss.examples.deltaspike.expensetracker.data.resources.MainEMResolver;
import org.jboss.examples.deltaspike.expensetracker.domain.model.ExpenseReport;
import org.jboss.examples.deltaspike.expensetracker.domain.model.Reimbursement;

@ApplicationScoped
@Repository
@EntityManagerConfig(entityManagerResolver = MainEMResolver.class)
public interface ReimbursementRepository extends EntityRepository<Reimbursement, Long> {

    public List<Reimbursement> findByReport(ExpenseReport report);

    public List<Reimbursement> findByDateBetween(Date from, Date to);

}
