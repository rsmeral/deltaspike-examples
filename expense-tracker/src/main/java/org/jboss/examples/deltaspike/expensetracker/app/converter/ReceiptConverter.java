package org.jboss.examples.deltaspike.expensetracker.app.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import javax.inject.Named;
import org.jboss.examples.deltaspike.expensetracker.data.ReceiptRepository;
import org.jboss.examples.deltaspike.expensetracker.domain.model.Receipt;

@Named
@FacesConverter(forClass = Receipt.class)
public class ReceiptConverter implements Converter {

    @Inject
    private ReceiptRepository repo;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return repo.findBy(Long.valueOf(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return ((Receipt) value).getId().toString();
    }

}
