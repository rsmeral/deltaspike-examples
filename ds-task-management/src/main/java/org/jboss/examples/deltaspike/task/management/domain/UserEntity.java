package org.jboss.examples.deltaspike.task.management.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class UserEntity implements Serializable {

    private static final long serialVersionUID = 5528794176369784915L;

    @Id
    private String login;
    private String name;
    private String surname;
    private boolean administator;
    private String password;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy = "userEntity")
    private List<TaskEntity> tasks;

    public UserEntity() {
    }

    public UserEntity(String login, String name, String surname, boolean administator, String password) {
        super();
        this.login = login;
        this.name = name;
        this.surname = surname;
        this.administator = administator;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public boolean isAdministator() {
        return administator;
    }

    public void setAdministator(boolean administator) {
        this.administator = administator;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<TaskEntity> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskEntity> tasks) {
        this.tasks = tasks;
    }

}
