package org.jboss.examples.deltaspike.expensetracker.app.resources;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import org.apache.deltaspike.data.api.EntityManagerResolver;

/**
 * In this application, two persistence units are used, one for application
 * data, other for identity storage. Even though DeltaSpike would automatically
 * use the @Default EntityManager for a @Repository, we use the @Main qualifier
 * to distinguish the application data EM and the @PicketLink identity EM, for
 * demonstrational purposes.
 */
public class MainEMResolver implements EntityManagerResolver {

    @Inject
    @Main
    private EntityManager em;

    @Override
    public EntityManager resolveEntityManager() {
        return em;
    }

}
