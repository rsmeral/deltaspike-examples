package org.jboss.examples.deltaspike.expensetracker.app;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import org.apache.deltaspike.core.api.provider.BeanManagerProvider;
import org.apache.deltaspike.core.util.metadata.builder.AnnotatedTypeBuilder;
import org.apache.deltaspike.jpa.api.transaction.Transactional;
import org.jboss.examples.deltaspike.expensetracker.domain.model.Employee;
import org.jboss.examples.deltaspike.expensetracker.domain.model.EmployeeRole;
import org.jboss.examples.deltaspike.expensetracker.service.EmployeeService;
import org.picketlink.annotations.PicketLink;
import org.picketlink.idm.IdentityManager;
import org.picketlink.idm.model.basic.Role;

/**
 * We need to make transactional calls to PicketLink methods, since we use a
 * container managed persistence context.
 */
@ApplicationScoped
@Transactional(qualifier = PicketLink.class)
public class IDMInitializer {

    public static final String USER_ADMIN = "admin";
    public static final String USER_ACCOUNTANT = "anna";
    public static final String USER_EMPLOYEE = "john";

    @Inject
    private IdentityManager idm;

    public void initializeRoles() {
        for (String role : EmployeeRole.getAllRoles()) {
            idm.add(new Role(role));
        }
    }

    public void registerEmployee(Employee emp, String username, String password, String... roles) {

        /*
         * Subvert the Security Interceptor by manually obtaining and injecting
         * an instance of EmployeeService bean. This is needed because the
         * EmployeeService#registerEmployee does a role check for admin, but we
         * need to register admin first without the security check.
         */
        getInjectedBeanInstance(EmployeeService.class).registerEmployee(emp, username, password, roles);
    }

    private <T> T getInjectedBeanInstance(Class<T> clazz) {
        try {
            T instance = clazz.getConstructor().newInstance();
            AnnotatedType<T> annotatedType = new AnnotatedTypeBuilder<T>().readFromType(clazz).create();
            BeanManager bm = BeanManagerProvider.getInstance().getBeanManager();
            Bean<T> bean = (Bean<T>) bm.getBeans(clazz).iterator().next();
            CreationalContext<T> cctx = bm.createCreationalContext(bean);
            bm.createInjectionTarget(annotatedType).inject(instance, cctx);
            return instance;
        } catch (Exception ex) {
            return null;
        }

    }

}
