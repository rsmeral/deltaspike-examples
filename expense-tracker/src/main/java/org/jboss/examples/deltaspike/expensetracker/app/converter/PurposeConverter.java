package org.jboss.examples.deltaspike.expensetracker.app.converter;

import javax.faces.convert.FacesConverter;
import javax.inject.Named;
import org.jboss.examples.deltaspike.expensetracker.data.PurposeRepository;
import org.jboss.examples.deltaspike.expensetracker.domain.model.Purpose;

@Named
@FacesConverter(forClass = Purpose.class)
public class PurposeConverter extends BaseEntityConverter<PurposeRepository, Purpose> {

}
