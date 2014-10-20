package org.jboss.examples.deltaspike.task.management.domain;

import org.apache.deltaspike.data.api.mapping.SimpleQueryInOutMapperBase;

public class TaskMapper extends SimpleQueryInOutMapperBase<TaskEntity, Task> {

    @Override
    protected Object getPrimaryKey(Task dto) {
        return dto.getId();
    }

    @Override
    protected Task toDto(TaskEntity entity) {
        User user = new User();
        user.setLogin(entity.getUserEntity().getLogin());

        Task task = new Task(entity.getName(), entity.getDescription(), user, entity.getDeadline(),
            entity.isFinished());
        task.setFinishDate(entity.getFinishDate());
        task.setId(entity.getId());

        return task;
    }

    @Override
    protected TaskEntity toEntity(TaskEntity entity, Task dto) {

        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());

        UserEntity userEntity = new UserEntity();
        userEntity.setLogin(dto.getUser().getLogin());
        entity.setUserEntity(userEntity);

        entity.setDeadline(dto.getDeadline());
        entity.setFinished(dto.isFinished());

        return entity;
    }

}
