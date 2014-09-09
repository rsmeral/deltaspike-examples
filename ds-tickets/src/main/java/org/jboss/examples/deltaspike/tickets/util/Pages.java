package org.jboss.examples.deltaspike.tickets.util;

import org.apache.deltaspike.core.api.config.view.ViewConfig;
import org.apache.deltaspike.jsf.api.config.view.View;
import org.apache.deltaspike.jsf.api.config.view.View.Extension;
import org.apache.deltaspike.jsf.api.config.view.View.NavigationMode;

@View(extension = Extension.JSF, navigation = NavigationMode.REDIRECT)
public interface Pages extends ViewConfig {

    @View(name = "bus_line")
    public class BusLine implements Pages {
    };

    public class Date implements Pages {
    };

    public class Seat implements Pages {
    };

    public class Overview implements Pages {
    };

    public class Ordered implements Pages{
    };

}
