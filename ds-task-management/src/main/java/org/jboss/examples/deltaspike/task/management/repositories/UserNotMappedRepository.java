package org.jboss.examples.deltaspike.task.management.repositories;

import java.util.List;

import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;
import org.jboss.examples.deltaspike.task.management.domain.User;
import org.jboss.examples.deltaspike.task.management.domain.UserEntity;

@Repository(forEntity = UserEntity.class)
public interface UserNotMappedRepository extends EntityRepository<User, String> {

    @Query("SELECT u.login FROM UserEntity u")
    public List<String> getAllUsersLogin();

}
