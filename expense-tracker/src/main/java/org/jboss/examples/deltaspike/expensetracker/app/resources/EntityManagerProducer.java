package org.jboss.examples.deltaspike.expensetracker.app.resources;

import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import org.picketlink.annotations.PicketLink;

/*
 * An entity manager exposed as a CDI bean is required for DeltaSpike's 
 * EntityRepository to work.
 */
public class EntityManagerProducer {

    @PersistenceUnit(unitName = "primary")
    private EntityManagerFactory primaryEmf;
    
//    @PersistenceUnit(unitName = "identity")
//    private EntityManagerFactory idEmf;

    @Produces
    public EntityManager producePrimaryEm() {
        return primaryEmf.createEntityManager();
    }

    public void destroyPrimaryEm(@Disposes EntityManager em) {
        if (em.isOpen()) {
            em.close();
        }
    }
    
//    @Produces
//    @PicketLink
//    public EntityManager produceIdentityEm() {
//        return idEmf.createEntityManager();
//    }

}
