package org.jboss.examples.deltaspike.task.management.repositories;

import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.SingleResultType;
import org.apache.deltaspike.data.api.mapping.MappingConfig;
import org.jboss.examples.deltaspike.task.management.domain.User;
import org.jboss.examples.deltaspike.task.management.domain.UserEntity;
import org.jboss.examples.deltaspike.task.management.domain.UserMapper;

@Repository(forEntity = UserEntity.class)
@MappingConfig(UserMapper.class)
public interface UserRepository extends EntityRepository<User, String> {

    @Query(value = "SELECT u FROM UserEntity u WHERE u.login = ?1 AND u.password = ?2", singleResult = SingleResultType.OPTIONAL)
    public User authorizeUser(String login, String password);

}
