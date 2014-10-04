package org.jboss.examples.deltaspike.expensetracker.domain.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Purpose extends BaseEntity {

    @Size(max = 25, min = 2)
    @NotNull
    private String name;

    private String description;

    public Purpose(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Purpose() {
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
}
