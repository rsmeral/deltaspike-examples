package org.jboss.examples.deltaspike.task.management.repositories;

import java.util.List;

import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.mapping.MappingConfig;
import org.jboss.examples.deltaspike.task.management.domain.Task;
import org.jboss.examples.deltaspike.task.management.domain.TaskMapper;
import org.jboss.examples.deltaspike.task.management.domain.User;
import org.jboss.examples.deltaspike.task.management.domain.UserEntity;

@Repository(forEntity = UserEntity.class)
@MappingConfig(TaskMapper.class)
public interface UserTaskMappedRepository extends EntityRepository<User, String> {

    @Query("SELECT u.tasks FROM UserEntity u WHERE u.login = ?1")
    public List<Task> getAllTasks(String login);

}
