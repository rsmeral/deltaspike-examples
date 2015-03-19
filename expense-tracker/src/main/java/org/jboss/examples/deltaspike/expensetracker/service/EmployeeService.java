package org.jboss.examples.deltaspike.expensetracker.service;

import org.apache.deltaspike.jpa.api.transaction.Transactional;
import org.jboss.examples.deltaspike.expensetracker.app.security.Authorizations;
import org.jboss.examples.deltaspike.expensetracker.data.EmployeeRepository;
import org.jboss.examples.deltaspike.expensetracker.domain.model.Employee;
import org.jboss.examples.deltaspike.expensetracker.domain.model.EmployeeRole;
import org.picketlink.annotations.PicketLink;
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
import org.picketlink.idm.query.IdentityQueryBuilder;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

import static org.jboss.examples.deltaspike.expensetracker.domain.model.EmployeeRole.ADMIN;

/**
 * Application layer service for registering Employees as system users. This
 * service bridges the application model and the IDM model.
 */
@ApplicationScoped
@Named
public class EmployeeService {

    public static final String EMPLOYEE_ID_ATTRIBUTE = "employeeId";

    @Inject
    private Event<Authorizations.Modified> authModifiedEvent;
    
    @Inject
    private IdentityManager idm;

    @Inject
    private RelationshipManager relMgr;

    @Inject
    private EmployeeRepository empRepo;

    public List<User> listUsers() {
        IdentityQuery<User> query = idm.getQueryBuilder().createIdentityQuery(User.class);
        return query.getResultList();
    }

    public User getUserForEmployee(Employee employee) {
        IdentityQueryBuilder builder = idm.getQueryBuilder();
        return builder.createIdentityQuery(User.class).where(builder.equal(new AttributeParameter(EMPLOYEE_ID_ATTRIBUTE), employee.getId())).getResultList().get(0);
    }

    public Employee getEmployeeByUsername(String username) {
        IdentityQueryBuilder builder = idm.getQueryBuilder();
        Long employeeId = builder.createIdentityQuery(User.class).where(builder.equal(User.LOGIN_NAME, username)).getResultList().get(0).<Long>getAttribute(EMPLOYEE_ID_ATTRIBUTE).getValue();
        return empRepo.findBy(employeeId);
    }

    public boolean isUsernameAvailable(String username) {
        IdentityQueryBuilder builder = idm.getQueryBuilder();
        int resultCount = builder.createIdentityQuery(User.class).where(builder.equal(User.LOGIN_NAME, username)).getResultCount();
        return resultCount == 0;
    }

    @RolesAllowed(ADMIN)
    @Transactional(qualifier = PicketLink.class)
    public void registerEmployee(Employee emp, String username, String password, String... roles) {
        User user = new User();
        user.setLoginName(username);
        user.setAttribute(new Attribute<Long>(EMPLOYEE_ID_ATTRIBUTE, emp.getId()));

        idm.add(user);
        idm.updateCredential(user, new Password(password));

        setRoles(user, roles);
    }

    @Transactional(qualifier = PicketLink.class)
    public void changePassword(Employee emp, String password) {
        User user = getUserForEmployee(emp);
        idm.updateCredential(user, new Password(password));
    }

    @Transactional(qualifier = PicketLink.class)
    public void setRoles(Employee emp, String... roles) {
        setRoles(getUserForEmployee(emp), roles);
        authModifiedEvent.fire(new Authorizations.Modified());
    }

    private void setRoles(IdentityType identity, String... roles) {
        for (String role : EmployeeRole.getAllRoles()) {
            BasicModel.revokeRole(relMgr, identity, BasicModel.getRole(idm, role));
        }

        for (String role : roles) {
            BasicModel.grantRole(relMgr, identity, BasicModel.getRole(idm, role));
        }
    }

    public List<String> getRoles(User user) {
        List<String> result = new ArrayList<String>();
        for (String role : EmployeeRole.getAllRoles()) {
            if (BasicModel.hasRole(relMgr, user, BasicModel.getRole(idm, role))) {
                result.add(role);
            }
        }
        return result;
    }
    
    public List<String> getRoles(Employee employee) {
        User user = getUserForEmployee(employee);
        return getRoles(user);
    }
}
