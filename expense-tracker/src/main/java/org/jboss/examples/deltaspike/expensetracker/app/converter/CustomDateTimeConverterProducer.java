package org.jboss.examples.deltaspike.expensetracker.app.converter;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.faces.convert.Converter;
import javax.faces.convert.DateTimeConverter;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.deltaspike.core.api.config.ConfigProperty;
import org.apache.deltaspike.core.api.exclude.Exclude;
import org.apache.deltaspike.core.api.projectstage.ProjectStage;

@Exclude(ifProjectStage = ProjectStage.IntegrationTest.class)
public class CustomDateTimeConverterProducer {

    @Inject
    @ConfigProperty(name = "dateTimePattern")
    private String dateTimePattern;

    @Produces
    @ApplicationScoped
    @Named("customDateTimeConverter")
    public Converter produce() {
        return new DateTimeConverter() {
            {
                setPattern(dateTimePattern);
            }

        };
    }

}
