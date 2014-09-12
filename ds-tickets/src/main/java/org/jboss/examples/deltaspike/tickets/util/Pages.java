package org.jboss.examples.deltaspike.tickets.util;

import org.apache.deltaspike.core.api.config.view.ViewConfig;
import org.apache.deltaspike.core.api.config.view.controller.ViewControllerRef;
import org.apache.deltaspike.jsf.api.config.view.View;
import org.apache.deltaspike.jsf.api.config.view.View.Extension;
import org.apache.deltaspike.jsf.api.config.view.View.NavigationMode;
import org.apache.deltaspike.jsf.api.config.view.View.ViewParameterMode;

@View(extension = Extension.XHTML, navigation = NavigationMode.REDIRECT)
public interface Pages extends ViewConfig {

    public interface Access extends Pages {

        @View(name = "bus_line")
        @ViewControllerRef(AppInitializer.class)
        public class BusLine implements Pages {
        }

        public class Date implements Pages {
        }

        public class Seat implements Pages {
        }

        public class Overview implements Pages {
        }
    }

    public interface Group extends Pages {

        @View(name = "single_page")
        @ViewControllerRef(AppInitializer.class)
        public class SinglePage implements Group {
        }
    }

    @View(viewParams = ViewParameterMode.INCLUDE)
    public class Ordered implements Pages {
    };

}
