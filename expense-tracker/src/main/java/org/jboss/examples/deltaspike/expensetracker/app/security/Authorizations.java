package org.jboss.examples.deltaspike.expensetracker.app.security;

import java.io.Serializable;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.deltaspike.core.api.scope.WindowScoped;
import org.jboss.examples.deltaspike.expensetracker.domain.model.EmployeeRole;
import org.picketlink.Identity;
import org.picketlink.idm.IdentityManager;
import org.picketlink.idm.RelationshipManager;
import org.picketlink.idm.model.basic.BasicModel;
import org.picketlink.idm.model.basic.Role;

/**
 * This is a utility bean that may be used by the view layer to determine
 * whether the current user has specific privileges.
 */
@Named("idm")
@WindowScoped
public class Authorizations implements Serializable {

    @Inject
    private Identity identity;

    @Inject
    private IdentityManager identityManager;

    @Inject
    private RelationshipManager relationshipManager;

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
}
