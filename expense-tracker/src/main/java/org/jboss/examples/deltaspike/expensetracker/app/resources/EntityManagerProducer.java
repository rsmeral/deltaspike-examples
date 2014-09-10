package org.jboss.examples.deltaspike.expensetracker.app.resources;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import org.apache.deltaspike.core.api.scope.WindowScoped;

/**
 * An entity manager exposed as a CDI bean is required for DeltaSpike's
 * EntityRepository to work.
 */
@ApplicationScoped
public class EntityManagerProducer {

    @PersistenceUnit(unitName = "primary")
    private EntityManagerFactory primaryEmf;

    /*
     * A conversation scoped entity manager avoids some trouble with transient
     * entity references and merging.
     */
    @Produces
    @WindowScoped
    public EntityManager producePrimaryEm() {
        return primaryEmf.createEntityManager();
    }

    public void dispose(@Disposes EntityManager entityManager) {
        if (entityManager.isOpen()) {
            entityManager.close();
        }
    }

}
