package org.jboss.examples.deltaspike.task.management.util;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

public class EntityManagerProducer {

    @PersistenceUnit
    private EntityManagerFactory emf;

    @Produces
    @Default
    @RequestScoped
    public EntityManager create() {
        return emf.createEntityManager();
    }

    public void close(@Disposes @Default EntityManager em) {
        if (em.isOpen()) {
            em.close();
        }
    }

}
