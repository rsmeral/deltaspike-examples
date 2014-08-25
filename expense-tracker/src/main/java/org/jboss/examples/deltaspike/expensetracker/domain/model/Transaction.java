package org.jboss.examples.deltaspike.expensetracker.domain.model;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Money transaction.
 */
@MappedSuperclass
public class Transaction {

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "datum")
    private Date date;

    @NotNull
    @Column(name = "val")
    @Min(value = 0)
    private BigDecimal value;

    @ManyToOne(optional = false)
    @JoinColumn(name = "reportId")
    private ExpenseReport report;

    public ExpenseReport getReport() {
        return report;
    }

    public void setReport(ExpenseReport report) {
        this.report = report;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + (this.date != null ? this.date.hashCode() : 0);
        hash = 73 * hash + (this.value != null ? this.value.hashCode() : 0);
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
        final Transaction other = (Transaction) obj;
        if (this.date != other.date && (this.date == null || !this.date.equals(other.date))) {
            return false;
        }
        if (this.value != other.value && (this.value == null || !this.value.equals(other.value))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Transaction{"
                + "date=" + date
                + ", value=" + value
                + //                ", report=" + report +
                '}';
    }
}
