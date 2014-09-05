package org.jboss.examples.deltaspike.expensetracker.ftest.util;

import java.util.Date;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Named;

/*
 * Converter for Date <-> UNIX timestamps, for testability. Normally, 
 * a functional test should test the application as it is, but this test 
 * focuses on testing DeltaSpike features.
 */
@Named("customDateTimeConverter")
@ApplicationScoped
public class TimestampConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        Date date = new Date(); 
        date.setTime(Long.valueOf(value));
        return date;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return Long.toString(((Date)value).getTime());
    }

}
