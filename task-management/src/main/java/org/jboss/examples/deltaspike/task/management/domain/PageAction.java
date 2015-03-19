package org.jboss.examples.deltaspike.task.management.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.deltaspike.data.api.audit.CreatedOn;
import org.apache.deltaspike.data.api.audit.ModifiedBy;
import org.jboss.examples.deltaspike.task.management.pages.PageEvent;

@Entity
public class PageAction implements Serializable{

    private static final long serialVersionUID = -7711130070072200008L;

    @Id
    @GeneratedValue
    private Long id;

    private String page;
    private PageEvent pageEvent;

    @Temporal(TemporalType.TIMESTAMP)
    @CreatedOn
    private Date called;

    @ModifiedBy
    private String auditUser;

    public PageAction() {
    }

    public PageAction(String page, PageEvent pageEvent) {
        super();
        this.page = page;
        this.pageEvent = pageEvent;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public PageEvent getPageEvent() {
        return pageEvent;
    }

    public void setPageEvent(PageEvent pageEvent) {
        this.pageEvent = pageEvent;
    }

    public Date getCalled() {
        return called;
    }

    public void setCalled(Date called) {
        this.called = called;
    }

    public String getAuditUser() {
        return auditUser;
    }

    public void setAuditUser(String auditUser) {
        this.auditUser = auditUser;
    }

}
