package org.jboss.examples.deltaspike.expensetracker.app.converter;

import javax.faces.convert.FacesConverter;
import javax.inject.Named;
import org.jboss.examples.deltaspike.expensetracker.data.EmployeeRepository;
import org.jboss.examples.deltaspike.expensetracker.domain.model.Employee;

@Named
@FacesConverter(forClass = Employee.class)
public class EmployeeConverter extends BaseEntityConverter<EmployeeRepository, Employee> {
}
