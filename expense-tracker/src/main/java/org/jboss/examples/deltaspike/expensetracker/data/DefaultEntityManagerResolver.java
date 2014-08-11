package org.jboss.examples.deltaspike.expensetracker.data;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import org.apache.deltaspike.data.api.EntityManagerResolver;

public class DefaultEntityManagerResolver implements EntityManagerResolver {

    @Inject
    private EntityManager em;

    @Override
    public EntityManager resolveEntityManager() {
        return em;
    }

}
