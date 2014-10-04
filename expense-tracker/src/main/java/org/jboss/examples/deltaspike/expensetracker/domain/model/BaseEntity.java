package org.jboss.examples.deltaspike.expensetracker.domain.model;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;

@MappedSuperclass
public abstract class BaseEntity implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BaseEntity other = (BaseEntity) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        List<String> attrList = new ArrayList<String>();

        for (Field f : this.getClass().getDeclaredFields()) {
            OneToMany oneToMany = f.getAnnotation(OneToMany.class);
            if (oneToMany == null) {
                try {
                    attrList.add(f.getName() + "=" + f.get(this).toString());
                } catch (Exception ex) {
                }
            }

        }
        return this.getClass().getSimpleName() + attrList.toString();
    }
}
