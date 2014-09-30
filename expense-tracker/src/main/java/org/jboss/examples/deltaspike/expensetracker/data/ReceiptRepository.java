package org.jboss.examples.deltaspike.expensetracker.data;

import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import org.apache.deltaspike.data.api.EntityManagerConfig;
import org.apache.deltaspike.data.api.EntityManagerDelegate;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.jboss.examples.deltaspike.expensetracker.data.resources.ReceiptsEMResolver;
import org.jboss.examples.deltaspike.expensetracker.domain.model.ExpenseReport;
import org.jboss.examples.deltaspike.expensetracker.domain.model.Receipt;

@ApplicationScoped
@Repository
@EntityManagerConfig(entityManagerResolver = ReceiptsEMResolver.class)
public interface ReceiptRepository extends EntityRepository<Receipt, Long>, EntityManagerDelegate<Receipt> {

    public List<Receipt> findByReport(ExpenseReport report);

}
