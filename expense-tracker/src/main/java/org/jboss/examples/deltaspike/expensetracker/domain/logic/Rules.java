package org.jboss.examples.deltaspike.expensetracker.domain.logic;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.deltaspike.core.api.provider.BeanProvider;
import org.apache.deltaspike.core.util.metadata.AnnotationInstanceProvider;
import org.apache.deltaspike.security.api.authorization.Secures;
import org.jboss.examples.deltaspike.expensetracker.app.security.Authorizations;
import org.jboss.examples.deltaspike.expensetracker.domain.model.ExpenseReport;

public interface Rules extends Serializable {

    public static class Producer {

        @Inject
        private Authorizations auth;

        @Produces
        @Named("rules")
        @SessionScoped
        public Rules produceRules() {
            if (auth.isAdmin()) {
                // the qualifier is needed due to DELTASPIKE-739
                return BeanProvider.getContextualReference(AdminRules.class, AnnotationInstanceProvider.of(AdminRules.Annotation.class));
            } else {
                return BeanProvider.getContextualReference(BaseRules.class);
            }
        }

    }

    /*
     * COMPLEX RULES
     */
    @Secures
    @Operation(value = Operation.Type.APPROVE)
    boolean canBeApproved(@Selected ExpenseReport selected);

    @Secures
    @Operation(value = Operation.Type.ASSIGN)
    boolean canBeAssigned(@Selected ExpenseReport selected);

    @Secures
    @Operation(value = Operation.Type.OPEN)
    boolean canBeOpened(@Selected ExpenseReport selected);

    @Secures
    @Operation(value = Operation.Type.REJECT)
    boolean canBeRejected(@Selected ExpenseReport selected);

    @Secures
    @Operation(value = Operation.Type.SETTLE)
    boolean canBeSettled(@Selected ExpenseReport selected);

    @Secures
    @Operation(value = Operation.Type.SUBMIT)
    boolean canBeSubmitted(@Selected ExpenseReport selected);

    @Secures
    @Operation(value = Operation.Type.UNASSIGN)
    boolean canBeUnassigned(@Selected ExpenseReport selected);

    boolean canEditExpenses(ExpenseReport selected);

    boolean canEditReceipts(ExpenseReport selected);

    boolean canEditReimbursements(ExpenseReport selected);

    boolean canEditReport(ExpenseReport selected);

    /*
     * AUTHORIZATION-BASED RULES
     */
    boolean canUserEditReport(ExpenseReport report);

    boolean canUserEditExpenses(ExpenseReport report);

    boolean canUserEditReceipts(ExpenseReport report);

    boolean canUserEditReimbursements(ExpenseReport report);

}
