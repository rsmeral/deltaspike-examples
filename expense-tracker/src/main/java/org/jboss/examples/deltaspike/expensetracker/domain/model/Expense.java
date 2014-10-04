package org.jboss.examples.deltaspike.expensetracker.domain.model;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Expense extends Transaction {

    @ManyToOne(optional = false, cascade = CascadeType.MERGE)
    @NotNull
    private Purpose purpose;

    @ManyToOne()
    private Receipt receipt;

    public Expense() {
    }

    public Expense(Purpose purpose, Receipt receipt, Date date, BigDecimal value, ExpenseReport report) {
        super(date, value, report);
        this.purpose = purpose;
        this.receipt = receipt;
    }

    public Purpose getPurpose() {
        return purpose;
    }

    public void setPurpose(Purpose purpose) {
        this.purpose = purpose;
    }

    public Receipt getReceipt() {
        return receipt;
    }

    public void setReceipt(Receipt receipt) {
        this.receipt = receipt;
    }
}
