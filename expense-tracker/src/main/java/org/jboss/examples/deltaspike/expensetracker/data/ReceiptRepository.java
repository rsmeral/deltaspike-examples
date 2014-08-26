package org.jboss.examples.deltaspike.expensetracker.data;

import javax.enterprise.context.ApplicationScoped;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.jboss.examples.deltaspike.expensetracker.domain.model.Receipt;

@ApplicationScoped
@Repository
public interface ReceiptRepository extends EntityRepository<Receipt, Long> {

}
