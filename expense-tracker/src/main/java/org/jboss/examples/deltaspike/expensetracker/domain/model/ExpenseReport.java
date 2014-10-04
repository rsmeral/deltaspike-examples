package org.jboss.examples.deltaspike.expensetracker.domain.model;

import java.util.Date;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import org.apache.deltaspike.data.api.audit.ModifiedBy;
import org.apache.deltaspike.data.api.audit.ModifiedOn;
import org.apache.deltaspike.data.impl.audit.AuditEntityListener;

@Entity
@EntityListeners(AuditEntityListener.class)
public class ExpenseReport extends BaseEntity {

    @NotNull
    private String name;

    private String description;

    @NotNull
    @ManyToOne(cascade = CascadeType.MERGE)
    private Employee reporter;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Employee assignee;

    @OneToMany(mappedBy = "report", cascade = CascadeType.MERGE)
    private List<Expense> expenses;

    @OneToMany(mappedBy = "report", cascade = CascadeType.MERGE)
    private List<Reimbursement> reimbursements;

    @OneToMany(mappedBy = "report", cascade = CascadeType.MERGE)
    private List<Receipt> receipts;

    /**
     * Tracks last modification date using DeltaSpike Auditing API.
     */
    @ModifiedOn(onCreate = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastChangeDate;

    /**
     * Tracks last modification author using DeltaSpike Auditing API.
     * The current user is obtained using {@link org.jboss.examples.deltaspike.expensetracker.app.CurrentEmployeeProducer}
     *
     * @see org.apache.deltaspike.data.api.audit.CurrentUser
     */
    @ModifiedBy
    @ManyToOne
    private Employee lastChangedBy;

    @Enumerated(EnumType.ORDINAL)
    private ReportStatus status;

    public ExpenseReport() {
    }

    public ExpenseReport(String name, String description, Employee reporter, Employee assignee, ReportStatus status) {
        this.name = name;
        this.description = description;
        this.reporter = reporter;
        this.assignee = assignee;
        this.status = status;
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

    public Employee getReporter() {
        return reporter;
    }

    public void setReporter(Employee reporter) {
        this.reporter = reporter;
    }

    public Employee getAssignee() {
        return assignee;
    }

    public void setAssignee(Employee assignee) {
        this.assignee = assignee;
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<Expense> expenses) {
        this.expenses = expenses;
    }

    public List<Reimbursement> getReimbursements() {
        return reimbursements;
    }

    public void setReimbursements(List<Reimbursement> reimbursements) {
        this.reimbursements = reimbursements;
    }

    public List<Receipt> getReceipts() {
        return receipts;
    }

    public void setReceipts(List<Receipt> receipts) {
        this.receipts = receipts;
    }

    public Date getLastChangeDate() {
        return lastChangeDate;
    }

    public void setLastChangeDate(Date lastChangeDate) {
        this.lastChangeDate = lastChangeDate;
    }

    public Employee getLastChangedBy() {
        return lastChangedBy;
    }

    public void setLastChangedBy(Employee lastChangedBy) {
        this.lastChangedBy = lastChangedBy;
    }

    public ReportStatus getStatus() {
        return status;
    }

    public void setStatus(ReportStatus status) {
        this.status = status;
    }

}
