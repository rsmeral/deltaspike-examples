package org.jboss.examples.deltaspike.expensetracker.app.resources;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.BeforeBeanDiscovery;
import javax.enterprise.inject.spi.Extension;
import org.apache.deltaspike.core.util.metadata.builder.AnnotatedTypeBuilder;
import org.apache.deltaspike.servlet.api.resourceloader.WebResourceProvider;

// no longer necessary as of DS 1.1.0, commented out in META-INF/services/javax.enterprise.inject.spi.Extension
public class DELTASPIKE732Hack implements Extension {

    public void addWebResourceProviderAsBean(@Observes BeforeBeanDiscovery bbd) {
        bbd.addAnnotatedType(new AnnotatedTypeBuilder<WebResourceProvider>().readFromType(WebResourceProvider.class).create());
    }
}
