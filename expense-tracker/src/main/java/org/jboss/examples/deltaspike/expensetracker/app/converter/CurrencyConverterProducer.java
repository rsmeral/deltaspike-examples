package org.jboss.examples.deltaspike.expensetracker.app.converter;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.faces.convert.Converter;
import javax.faces.convert.NumberConverter;
import javax.inject.Named;
import org.apache.deltaspike.core.api.exclude.Exclude;
import org.apache.deltaspike.core.api.projectstage.ProjectStage;

@Exclude(ifProjectStage = ProjectStage.IntegrationTest.class)
public class CurrencyConverterProducer {

    @Produces
    @ApplicationScoped
    @Named("currencyConverter")
    public Converter produce() {
        return new NumberConverter() {
            {
                setLocale(null);
                setCurrencySymbol("$");
                setMaxFractionDigits(0);
                setType("currency");
            }
        };
    }

}
