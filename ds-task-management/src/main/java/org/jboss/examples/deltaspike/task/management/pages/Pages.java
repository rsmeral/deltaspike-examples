package org.jboss.examples.deltaspike.task.management.pages;

import org.apache.deltaspike.core.api.config.view.ViewConfig;
import org.apache.deltaspike.core.api.config.view.controller.ViewControllerRef;
import org.apache.deltaspike.jsf.api.config.view.View;
import org.apache.deltaspike.jsf.api.config.view.View.Extension;
import org.apache.deltaspike.jsf.api.config.view.View.NavigationMode;
import org.apache.deltaspike.security.api.authorization.Secured;
import org.jboss.examples.deltaspike.task.management.security.AdminDecisionVoter;
import org.jboss.examples.deltaspike.task.management.security.LoggedInDecisionVoter;

@View(extension = Extension.XHTML, navigation = NavigationMode.REDIRECT)
public interface Pages extends ViewConfig {

    public class Login implements Pages {
    }

    @View(name = "access_denied")
    @ViewControllerRef(value = PagesCallbackHandler.class)
    public class AccessDenied implements Pages {
    }

    @ViewControllerRef(value = PagesCallbackHandler.class)
    public class Home implements Pages {
    }

    @Secured(value = LoggedInDecisionVoter.class)
    @ViewControllerRef(value = PagesCallbackHandler.class)
    public interface Protected extends Pages {

        @View(name = "my_tasks")
        public class MyTasks implements Protected {
        }

        @Secured(value = AdminDecisionVoter.class)
        public interface Admin extends Protected {

            @View(name = "create_task")
            public class CreateTask implements Admin {
            }

            @View(name = "task_created")
            public class TaskCreated implements Admin {
            }

            @View(name = "web_log")
            public class WebLog implements Admin {
            }
        }
    }
}
