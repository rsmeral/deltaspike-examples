package org.jboss.examples.deltaspike.expensetracker.service;

import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.jboss.examples.deltaspike.expensetracker.model.EmployeeRole;
import static org.jboss.examples.deltaspike.expensetracker.model.EmployeeRole.*;
import org.jboss.examples.deltaspike.expensetracker.model.Employee;
import org.picketlink.authorization.annotations.RolesAllowed;
import org.picketlink.idm.IdentityManager;
import org.picketlink.idm.RelationshipManager;
import org.picketlink.idm.credential.Password;
import org.picketlink.idm.model.Attribute;
import org.picketlink.idm.model.IdentityType;
import org.picketlink.idm.model.basic.BasicModel;
import org.picketlink.idm.model.basic.User;
import org.picketlink.idm.query.AttributeParameter;
import org.picketlink.idm.query.IdentityQuery;

/**
 * Application layer service for registering Employees as system users. This
 * service bridges the application model and the IDM model.
 */
@ApplicationScoped
@RolesAllowed(ADMIN)
@Named
public class EmployeeService {

    public static final String EMPLOYEE_ID_ATTRIBUTE = "employeeId";

    @Inject
    private IdentityManager idm;

    @Inject
    private RelationshipManager relMgr;

    public List<User> listUsers() {
        IdentityQuery<User> query = idm.createIdentityQuery(User.class);
        return query.getResultList();
    }

    public User getUserForEmployee(Employee employee) {
        return idm.createIdentityQuery(User.class).setParameter(new AttributeParameter(EMPLOYEE_ID_ATTRIBUTE), employee.getId()).getResultList().get(0);
    }

    public void registerEmployee(Employee emp, String username, String password, String... roles) {
        User user = new User();
        user.setLoginName(username);
        user.setAttribute(new Attribute<Long>(EMPLOYEE_ID_ATTRIBUTE, emp.getId()));

        idm.add(user);
        idm.updateCredential(user, new Password(password));

        setRoles(user, roles);

    }

    public void setRoles(IdentityType identity, String... roles) {
        for (String role : EmployeeRole.getAllRoles()) {
            BasicModel.revokeRole(relMgr, identity, BasicModel.getRole(idm, role));
        }

        for (String role : roles) {
            BasicModel.grantRole(relMgr, identity, BasicModel.getRole(idm, role));
        }
    }
}
