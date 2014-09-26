package org.jboss.examples.deltaspike.expensetracker.app.resources;

import java.util.Properties;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.deltaspike.core.api.resourceloader.InjectableResource;
import org.apache.deltaspike.servlet.api.resourceloader.WebResourceProvider;

/**
 * Exposes properties pulled from a web resource. There are indeed simpler and
 * more efficient ways to achieve the goal of showing dependency versions in the
 * application interface, but this is here to demonstrate usage of injectable
 * web resources.
 */
@Named("dependencies")
@ApplicationScoped
public class DependencyVersions {

    private static final String VERSIONS_FILE = "dependency.versions";

    @Produces
    @Named
    public static final String DELTASPIKE = "deltaspike";

    @Inject
    @InjectableResource(resourceProvider = WebResourceProvider.class, location = VERSIONS_FILE)
    private Properties versions;

    public String getVersion(String componentName) {
        return versions.getProperty(componentName);
    }

}
