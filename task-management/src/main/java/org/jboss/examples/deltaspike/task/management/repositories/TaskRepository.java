package org.jboss.examples.deltaspike.task.management.repositories;

import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.mapping.MappingConfig;
import org.jboss.examples.deltaspike.task.management.domain.Task;
import org.jboss.examples.deltaspike.task.management.domain.TaskEntity;
import org.jboss.examples.deltaspike.task.management.domain.TaskMapper;

@Repository(forEntity = TaskEntity.class)
@MappingConfig(TaskMapper.class)
public interface TaskRepository extends EntityRepository<Task, Long> {

}
