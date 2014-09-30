package org.jboss.examples.deltaspike.expensetracker.data.resources;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.*;
import org.picketlink.annotations.PicketLink;

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
    @Main
    @ConversationScoped
    public EntityManager producePrimaryEm() {
        return primaryEmf.createEntityManager();
    }

    @Produces
    @Receipts
    @SessionScoped
    public EntityManager produceReceiptsEm() {
        return primaryEmf.createEntityManager();
    }

    /*
     * EntityManager required by PicketLink's JPA identity store
     */
    @Produces
    @PicketLink
    @PersistenceContext(unitName = "identity", type = PersistenceContextType.EXTENDED)
    private EntityManager identityEm;

}
