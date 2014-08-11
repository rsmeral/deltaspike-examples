package org.jboss.examples.deltaspike.expensetracker.app.security;

import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import static org.jboss.examples.deltaspike.expensetracker.app.security.EmployeeRole.*;
import org.jboss.examples.deltaspike.expensetracker.model.Employee;
import org.picketlink.authorization.annotations.RolesAllowed;
import org.picketlink.idm.IdentityManager;
import org.picketlink.idm.RelationshipManager;
import org.picketlink.idm.credential.Password;
import org.picketlink.idm.model.Attribute;
import org.picketlink.idm.model.IdentityType;
import org.picketlink.idm.model.basic.BasicModel;
import org.picketlink.idm.model.basic.Role;
import org.picketlink.idm.model.basic.User;
import org.picketlink.idm.query.IdentityQuery;

/*
 * Application layer service for registering Employees as system users
 */
@ApplicationScoped
@RolesAllowed(ADMIN_ROLE)
public class EmployeeRegistration {

    public static final String EMPLOYEE_ID_ATTRIBUTE = "employeeId";

    @Inject
    private IdentityManager idm;

    @Inject
    private RelationshipManager relMgr;

    public List<User> listUsers() {
        IdentityQuery<User> query = idm.createIdentityQuery(User.class);
        return query.getResultList();
    }

    public void registerEmployee(Employee emp, String username, String password, String... roles) {
        User user = new User();
        user.setLoginName(username);
        user.setAttribute(new Attribute<Long>(EMPLOYEE_ID_ATTRIBUTE, emp.getId()));

        idm.add(user);
        idm.updateCredential(user, new Password(password));
        
        for (String role : roles) {
            BasicModel.grantRole(relMgr, user, BasicModel.getRole(idm, role));
        }

    }

    public void setRoles(IdentityType identity, String... roles) {
        for (String role : EmployeeRole.getAllRoles()) {
            BasicModel.revokeRole(relMgr, identity, new Role(role));
        }

        for (String role : roles) {
            BasicModel.grantRole(relMgr, identity, new Role(role));
        }
    }
}
