package org.jboss.examples.deltaspike.task.management.test;

import java.io.Serializable;
import java.util.Date;

public class PageAction implements Serializable {

    private static final long serialVersionUID = -7711130070072200008L;

    private String page;
    private PageEvent pageEvent;
    private Date called;
    private String auditUser;

    public PageAction() {
    }

    public PageAction(String page, String[] auditUser, PageEvent pageEvent) {
        super();
        this.page = page;
        this.pageEvent = pageEvent;
        if (auditUser != null) {
            this.auditUser = auditUser[0] + " (" + auditUser[2] + ")";
        } else {
            this.auditUser = "null (null null)";
        }
        called = new Date();
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

    @Override
    public String toString() {
        return page + "\n" + pageEvent + "\t" + called + "\t" + auditUser;
    }

}
