package org.jboss.examples.deltaspike.expensetracker.model;

/**
 * Transition between states of an expense report.
 * <ul>
 * <li>OPEN - new or modified</li>
 * <li>SUBMITTED - submitted for approval</li>
 * <li>APPROVED - approved for reimbursement/ready for money transfer</li>
 * <li>REJECTED - expense report needs correction</li>
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
