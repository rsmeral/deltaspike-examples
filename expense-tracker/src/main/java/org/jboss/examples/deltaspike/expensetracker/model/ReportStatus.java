package org.jboss.examples.deltaspike.expensetracker.model;

/**
 * Transition between states of cz.muni.fi.pv243.et.model.ExpenseReport.
 * <ul>
 * <li>OPEN - new or modified</li>
 * <li>SUBMITTED - submitted for approval</li>
 * <li>APPROVED - approved for reimbursement/ready for money transfer</li>
 * <li>REJECTED - cz.muni.fi.pv243.et.model.ExpenseReport needs correction</li>
 * <li>SETTLED - both parties satisfied, money exchanged</li>
 * </ul>
 *
 */
public enum ReportStatus {

    OPEN,
    SUBMITTED,
    APPROVED,
    REJECTED,
    SETTLED
}
