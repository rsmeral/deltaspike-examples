package org.jboss.examples.deltaspike.expensetracker.domain.model;

import java.util.Date;
import java.util.List;
import javax.persistence.*;
import org.apache.deltaspike.data.api.audit.CreatedOn;
import org.apache.deltaspike.data.impl.audit.AuditEntityListener;

@Entity
@EntityListeners(AuditEntityListener.class)
public class Receipt extends BaseEntity {

    public Receipt(Date importDate, Employee importedBy, byte[] document, String documentName, ExpenseReport report) {
        this.importDate = importDate;
        this.importedBy = importedBy;
        this.document = document;
        this.documentName = documentName;
        this.report = report;
    }

    public Receipt() {
    }

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @CreatedOn
    private Date importDate;

    @ManyToOne(optional = false, cascade = CascadeType.MERGE)
    private Employee importedBy;

    @Lob
    private byte[] document;

    private String documentName;

    @OneToMany(mappedBy = "receipt")
    private List<Expense> expenses;

    @ManyToOne(optional = false)
    private ExpenseReport report;

    public Date getImportDate() {
        return importDate;
    }

    public void setImportDate(Date importDate) {
        this.importDate = importDate;
    }

    public Employee getImportedBy() {
        return importedBy;
    }

    public void setImportedBy(Employee importedBy) {
        this.importedBy = importedBy;
    }

    public byte[] getDocument() {
        return document;
    }

    public void setDocument(byte[] document) {
        this.document = document;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }


    public ExpenseReport getReport() {
        return report;
    }

    public void setReport(ExpenseReport report) {
        this.report = report;
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<Expense> expenses) {
        this.expenses = expenses;
    }

}
