package org.jboss.examples.deltaspike.expensetracker.app.resources;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.PersistenceUnit;
import org.picketlink.annotations.PicketLink;

/**
 * An entity manager exposed as a CDI bean is required for DeltaSpike's
 * EntityRepository to work.
 */
@ApplicationScoped
public class EntityManagerProducer {

    @PersistenceUnit(unitName = "primary")
    private EntityManagerFactory primaryEmf;

    @Produces
    @PicketLink
    @PersistenceContext(unitName = "identity", type = PersistenceContextType.EXTENDED)
    private EntityManager identityEm;

    /*
     * A conversation scoped entity manager avoids some trouble with transient
     * entity references and merging.
     */
    @Produces
    @ConversationScoped
    public EntityManager producePrimaryEm() {
        return primaryEmf.createEntityManager();
    }

}
