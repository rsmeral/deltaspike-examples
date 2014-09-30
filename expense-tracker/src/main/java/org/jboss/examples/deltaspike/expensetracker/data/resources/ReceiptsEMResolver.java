package org.jboss.examples.deltaspike.expensetracker.data.resources;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import org.apache.deltaspike.data.api.EntityManagerResolver;

public class ReceiptsEMResolver implements EntityManagerResolver {

    @Inject
    @Receipts
    private EntityManager em;

    @Override
    public EntityManager resolveEntityManager() {
        return em;
    }

}
