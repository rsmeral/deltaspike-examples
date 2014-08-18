package org.jboss.examples.deltaspike.expensetracker.model;

import java.util.Arrays;
import java.util.List;
import javax.enterprise.inject.Produces;
import javax.inject.Named;

public enum Currency {

    CZK,
    USD,
    EUR,
    GBP;

    @Produces
    @Named("currencies")
    public List<Currency> getAllCurrencies() {
        return Arrays.asList(Currency.values());
    }

}
