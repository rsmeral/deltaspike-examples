/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.examples.deltaspike.expensetracker.app.security.view;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.deltaspike.data.api.audit.CurrentUser;
import org.jboss.examples.deltaspike.expensetracker.app.security.EmployeeRole;
import static org.jboss.examples.deltaspike.expensetracker.app.security.EmployeeRole.ACCOUNTANT;
import static org.jboss.examples.deltaspike.expensetracker.app.security.EmployeeRole.ADMIN;
import static org.jboss.examples.deltaspike.expensetracker.app.security.EmployeeRole.EMPLOYEE;
import org.jboss.examples.deltaspike.expensetracker.model.Employee;
import org.jboss.examples.deltaspike.expensetracker.model.ExpenseReport;
import static org.jboss.examples.deltaspike.expensetracker.model.ReportStatus.OPEN;
import static org.jboss.examples.deltaspike.expensetracker.model.ReportStatus.SUBMITTED;
import org.picketlink.Identity;
import org.picketlink.idm.IdentityManager;
import org.picketlink.idm.RelationshipManager;
import org.picketlink.idm.model.basic.BasicModel;
import org.picketlink.idm.model.basic.Role;

/**
 * This is a utility bean that may be used by the view layer to determine
 * whether the current user has specific privileges.
 *
 * @author Shane Bryzak
 *
 */
@Named("idm")
@RequestScoped
public class AuthorizationChecker {

    @Inject
    private Identity identity;

    @Inject
    private IdentityManager identityManager;

    @Inject
    private RelationshipManager relationshipManager;

    @Inject
    @CurrentUser
    private Employee currentEmployee;

    public boolean isAdmin() {
        return hasRole(EmployeeRole.ADMIN);
    }

    public boolean hasRole(String roleName) {
        Role role = BasicModel.getRole(this.identityManager, roleName);
        return BasicModel.hasRole(this.relationshipManager, this.identity.getAccount(), role);
    }

    public boolean hasAnyRole(String... roleNames) {
        for (String roleName : roleNames) {
            if (hasRole(roleName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Condition for report editing. An accountant can edit if: - he is not the
     * reporter, - he is the assignee, - report is in the SUBMITTED state.
     *
     * An employee can edit if: - he is the reporter, - report is in the OPEN
     * state.
     *
     * @param report
     * @return
     */
    public boolean canEditReport(ExpenseReport report) {
        if (isAdmin()) {
            return true;
        }
        if (hasRole(ACCOUNTANT)) {
            return !report.getReporter().equals(currentEmployee)
                    && report.getAssignee().equals(currentEmployee)
                    && report.getStatus().equals(SUBMITTED);
        } else if (hasRole(EMPLOYEE)) {
            return report.getReporter().equals(currentEmployee)
                    && report.getStatus().equals(OPEN);
        }

        return hasRole(ADMIN);
    }

    /**
     * Reimbursements can be edited if the user is an accountant and the report
     * is editable.
     *
     * @param report
     * @return
     */
    public boolean canEditReimbursements(ExpenseReport report) {
        if (isAdmin()) {
            return true;
        }
        return canEditReport(report)
                && hasRole(ACCOUNTANT);
    }

    /**
     * Reimbursements can be edited if the user is an employee and the report is
     * editable.
     *
     * @param report
     * @return
     */
    public boolean canEditExpenses(ExpenseReport report) {
        if (isAdmin()) {
            return true;
        }
        return canEditReport(report)
                && hasRole(EMPLOYEE);
    }
}
