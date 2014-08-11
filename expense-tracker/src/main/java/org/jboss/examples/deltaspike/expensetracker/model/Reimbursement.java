package org.jboss.examples.deltaspike.expensetracker.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Reimbursement extends Transaction implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(optional = false)
    @NotNull
    private Employee creator;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Employee getCreator() {
        return creator;
    }

    public void setCreator(Employee creator) {
        this.creator = creator;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Reimbursement)) {
            return false;
        }

        Reimbursement that = (Reimbursement) o;

        if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getId() != null ? getId().hashCode() : 0);
        return result;
    }

    private String getExpenseReport() {
        return "  reportId =" + getReport().getId() + " name=" + getReport().getName();
    }

    @Override
    public String toString() {
        return "\nMoneyTransfer{"
                + "id=" + id
                + ", creator=" + creator
                + getExpenseReport()
                + "}\t";
    }
}
