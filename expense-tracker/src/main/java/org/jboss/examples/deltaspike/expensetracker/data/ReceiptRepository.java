package org.jboss.examples.deltaspike.expensetracker.data;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.jboss.examples.deltaspike.expensetracker.model.Receipt;

@ApplicationScoped
@Repository
@Named
public abstract class ReceiptRepository implements EntityRepository<Receipt, Long> {

}
