package org.jboss.examples.deltaspike.expensetracker.domain.model;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Reimbursement extends Transaction {

    @ManyToOne(optional = false, cascade = CascadeType.MERGE)
    @NotNull
    private Employee creator;

    public Reimbursement() {
    }

    public Reimbursement(Employee creator, Date date, BigDecimal value, ExpenseReport report) {
        super(date, value, report);
        this.creator = creator;
    }

    public Employee getCreator() {
        return creator;
    }

    public void setCreator(Employee creator) {
        this.creator = creator;
    }
}
