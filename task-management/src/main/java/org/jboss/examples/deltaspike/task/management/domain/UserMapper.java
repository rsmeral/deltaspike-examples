package org.jboss.examples.deltaspike.task.management.domain;

import java.util.ArrayList;

import javax.inject.Inject;

import org.apache.deltaspike.data.api.mapping.SimpleQueryInOutMapperBase;

public class UserMapper extends SimpleQueryInOutMapperBase<UserEntity, User> {
    // public class UserMapper implements QueryInOutMapper<UserEntity> {
    @Inject
    private TaskMapper taskMapper;

    // @Override
    // public Object mapResult(UserEntity entity) {
    // User user = new User(entity.getLogin(), entity.getName(), entity.getSurname(), entity.isAdministator(), "");
    //
    // if (entity.getTasks() != null) {
    // ArrayList<Task> tasks = new ArrayList<Task>();
    // for (TaskEntity task : entity.getTasks()) {
    // tasks.add(taskMapper.toDto(task));
    // }
    // user.setTasks(tasks);
    // }
    //
    // return user;
    // }
    //
    // @Override
    // public Object mapResultList(List<UserEntity> result) {
    //
    // ArrayList<User> users = new ArrayList<User>();
    // if (result.size() != 0) {
    // if (!(result.get(0) instanceof UserEntity)) {
    // System.err.println(result);
    // return result;
    // }
    // for (UserEntity userEntity : result) {
    // users.add((User) mapResult(userEntity));
    // }
    // }
    // return users;
    // }
    //
    // @Override
    // public boolean mapsParameter(Object parameter) {
    //
    // return parameter != null && (parameter instanceof User);
    // }
    //
    // @Override
    // public Object mapParameter(Object parameter) {
    // User dto = (User) parameter;
    // UserEntity entity = new UserEntity();
    // entity.setLogin(dto.getLogin());
    // entity.setName(dto.getName());
    // entity.setSurname(dto.getSurname());
    // entity.setAdministator(dto.isAdministator());
    // entity.setPassword(dto.getPassword());
    //
    // if (dto.getTasks() != null) {
    // ArrayList<TaskEntity> tasks = new ArrayList<TaskEntity>();
    // for (Task task : dto.getTasks()) {
    // tasks.add(taskMapper.toEntity(new TaskEntity(), task));
    // }
    // entity.setTasks(tasks);
    // }
    //
    // return entity;
    // }

    @Override
    protected Object getPrimaryKey(User dto) {
        return dto.getLogin();
    }

    @Override
    protected User toDto(UserEntity entity) {
        User user = new User(entity.getLogin(), entity.getName(), entity.getSurname(), entity.isAdministator(), "");

        if (entity.getTasks() != null) {
            ArrayList<Task> tasks = new ArrayList<Task>();
            for (TaskEntity task : entity.getTasks()) {
                tasks.add(taskMapper.toDto(task));
            }
            user.setTasks(tasks);
        }

        return user;
    }

    @Override
    protected UserEntity toEntity(UserEntity entity, User dto) {
        if (entity == null) {
            entity = new UserEntity();
        }
        entity.setLogin(dto.getLogin());
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setAdministator(dto.isAdministator());
        entity.setPassword(dto.getPassword());

        if (dto.getTasks() != null) {
            ArrayList<TaskEntity> tasks = new ArrayList<TaskEntity>();
            for (Task task : dto.getTasks()) {
                tasks.add(taskMapper.toEntity(new TaskEntity(), task));
            }
            entity.setTasks(tasks);
        }

        return entity;
    }
}
