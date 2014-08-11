package org.jboss.examples.deltaspike.expensetracker.data;

import javax.enterprise.context.ApplicationScoped;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.jboss.examples.deltaspike.expensetracker.model.Receipt;

@ApplicationScoped
@Repository
public abstract class ReceiptRepository implements EntityRepository<Receipt, Long> {

}
