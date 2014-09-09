package org.jboss.examples.deltaspike.task.management.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.deltaspike.data.api.audit.ModifiedOn;

@Entity
public class TaskEntity implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 8656488095022688828L;

    @Id
    @GeneratedValue
    private Long Id;
    private String name;
    private String description;

    @ManyToOne(optional = false)
    private UserEntity userEntity;
    private Date deadline;

    @Temporal(TemporalType.TIMESTAMP)
    @ModifiedOn
    private Date finishDate;

    public TaskEntity() {
    }

    public TaskEntity(String name, String description, UserEntity userEntity, Date deadline, Date finishDate) {
        super();
        this.name = name;
        this.description = description;
        this.userEntity = userEntity;
        this.deadline = deadline;
        this.finishDate = finishDate;
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

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity user) {
        this.userEntity = user;
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
}
