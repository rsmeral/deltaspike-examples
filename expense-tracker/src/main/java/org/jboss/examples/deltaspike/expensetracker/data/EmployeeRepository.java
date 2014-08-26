package org.jboss.examples.deltaspike.expensetracker.data;

import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import org.apache.deltaspike.data.api.EntityManagerDelegate;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.jboss.examples.deltaspike.expensetracker.domain.model.Employee;

@ApplicationScoped
@Repository
public interface EmployeeRepository extends EntityRepository<Employee, Long>, EntityManagerDelegate<Employee> {

    public List<Employee> findByEmail(String email);

    public List<Employee> findByLastNameLike(String lastName);

}
