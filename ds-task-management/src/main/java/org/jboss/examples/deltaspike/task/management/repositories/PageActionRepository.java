package org.jboss.examples.deltaspike.task.management.repositories;

import java.util.List;

import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;
import org.jboss.examples.deltaspike.task.management.domain.PageAction;

@Repository
public interface PageActionRepository extends EntityRepository<PageAction, Long> {

    @Query("SELECT p FROM PageAction p ORDER BY p.called DESC")
    public List<PageAction> findAllOrdered();

}
