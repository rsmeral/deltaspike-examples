package org.jboss.examples.deltaspike.tickets.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;

@Model
public class Utils {

    public Utils() {
    }

    public String formatDate(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return formater.format(date);
    }

    public List<String> getAllBusSeats() {
        List<String> allSeats = new ArrayList<String>();

        for (int i = 0; i < 12; i++) {
            for (String s : new String[] { "A", "B", "C", "D" }) {
                allSeats.add((i + 1) + s);
            }
        }
        allSeats.remove("8C");
        allSeats.remove("8D");

        return allSeats;
    }

    @Produces
    @RequestScoped
    public FacesContext getFacesContext() {
        return FacesContext.getCurrentInstance();
    }

    public boolean isGroup() {
        return getFacesContext().getViewRoot().getViewId().contains("group");
    }
}
