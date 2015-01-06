package org.jboss.examples.deltaspike.expensetracker.app.resources;

import org.apache.deltaspike.core.api.config.PropertyFileConfig;

public class AppConfig implements  PropertyFileConfig {    
    
    @Override
    public String getPropertyFileName() {
        return "app.properties";
    }

	@Override
	public boolean isOptional() {
		return false;
	}
    
}
