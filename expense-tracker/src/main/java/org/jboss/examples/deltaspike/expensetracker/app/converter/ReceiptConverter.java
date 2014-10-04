package org.jboss.examples.deltaspike.expensetracker.app.converter;

import javax.faces.convert.FacesConverter;
import javax.inject.Named;
import org.jboss.examples.deltaspike.expensetracker.data.ReceiptRepository;
import org.jboss.examples.deltaspike.expensetracker.domain.model.Receipt;

@Named
@FacesConverter(forClass = Receipt.class)
public class ReceiptConverter extends BaseEntityConverter<ReceiptRepository, Receipt> {

}
