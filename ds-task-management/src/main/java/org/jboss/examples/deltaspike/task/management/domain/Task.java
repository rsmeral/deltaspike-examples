package org.jboss.examples.deltaspike.task.management.domain;

import java.io.Serializable;
import java.util.Date;

import javax.inject.Named;

import org.apache.deltaspike.core.api.scope.ViewAccessScoped;

@Named
@ViewAccessScoped
public class Task implements Serializable {

    private static final long serialVersionUID = 8656488095022688828L;

    private Long Id;
    private String name;
    private String description;
    private User user;
    private Date deadline;
    private Date finishDate;
    private boolean finished;

    public Task() {
        user = new User();
    }

    public Task(String name, String description, User user, Date deadline, boolean finished) {
        super();
        this.name = name;
        this.description = description;
        this.user = user;
        this.deadline = deadline;
        this.finished = finished;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

}
