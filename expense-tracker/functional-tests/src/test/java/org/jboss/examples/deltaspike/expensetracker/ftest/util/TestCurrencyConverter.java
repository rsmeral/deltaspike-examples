package org.jboss.examples.deltaspike.expensetracker.ftest.util;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.convert.NumberConverter;
import javax.inject.Named;

@Named("currencyConverter")
@ApplicationScoped
public class TestCurrencyConverter extends NumberConverter {

    {
        setLocale(null);
        setMaxFractionDigits(0);
        setCurrencySymbol("");
        setGroupingUsed(false);
        setType("number");
    }

}
