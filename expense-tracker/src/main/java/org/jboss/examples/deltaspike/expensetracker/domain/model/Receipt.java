package org.jboss.examples.deltaspike.expensetracker.domain.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import org.apache.deltaspike.data.api.audit.CreatedOn;
import org.apache.deltaspike.data.impl.audit.AuditEntityListener;

@Entity
@EntityListeners(AuditEntityListener.class)
public class Receipt implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    public Receipt(Date importDate, Employee importedBy, byte[] document, String documentName) {
        this.importDate = importDate;
        this.importedBy = importedBy;
        this.document = document;
        this.documentName = documentName;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    @Override
    public final boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Receipt)) {
            return false;
        }

        Receipt receipt = (Receipt) o;

        if (getId() != null ? !getId().equals(receipt.getId()) : receipt.getId() != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Receipt{"
                + "id=" + getId()
                + ", importDate=" + getImportDate()
                + ", importedBy=" + getImportedBy()
                + ", documentName='" + getDocumentName() + '\''
                + '}';
    }
}
