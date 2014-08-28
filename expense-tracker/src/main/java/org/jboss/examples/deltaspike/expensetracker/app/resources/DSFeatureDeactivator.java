package org.jboss.examples.deltaspike.expensetracker.app.resources;

import java.util.HashSet;
import java.util.Set;
import org.apache.deltaspike.core.spi.activation.ClassDeactivator;
import org.apache.deltaspike.core.spi.activation.Deactivatable;

/**
 * It's easy to deactivate particular DeltaSpike features which are not required
 * for this application.
 */
public class DSFeatureDeactivator implements ClassDeactivator {

    private final Set<String> deactivated = new HashSet<String>() {
        {
            add("org.apache.deltaspike.jsf.impl.scope.mapped.MappedJsf2ScopeExtension");
            add("org.apache.deltaspike.jsf.impl.scope.view.ViewScopedExtension");
            add("org.apache.deltaspike.core.impl.exclude.CustomProjectStageBeanFilter");
            add("org.apache.deltaspike.core.impl.jmx.MBeanExtension");

        }
    };

    @Override
    public Boolean isActivated(Class<? extends Deactivatable> type) {
        return !deactivated.contains(type.getName());
    }

}
