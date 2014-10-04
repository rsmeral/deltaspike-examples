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
public class Transaction extends BaseEntity {

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

    public Transaction() {
    }

    public Transaction(Date date, BigDecimal value, ExpenseReport report) {
        this.date = date;
        this.value = value;
        this.report = report;
    }

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
}
