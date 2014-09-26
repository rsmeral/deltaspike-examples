package org.jboss.examples.deltaspike.expensetracker.data;

import javax.enterprise.context.ApplicationScoped;
import org.apache.deltaspike.data.api.EntityManagerConfig;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.jboss.examples.deltaspike.expensetracker.app.resources.MainEMResolver;
import org.jboss.examples.deltaspike.expensetracker.domain.model.Purpose;

@ApplicationScoped
@Repository
@EntityManagerConfig(entityManagerResolver = MainEMResolver.class)
public interface PurposeRepository extends EntityRepository<Purpose, Long> {

}
