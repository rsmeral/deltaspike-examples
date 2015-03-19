package org.jboss.examples.deltaspike.task.management.managers;

import javax.enterprise.inject.Model;

import org.apache.deltaspike.core.api.config.view.ViewConfig;
import org.jboss.examples.deltaspike.task.management.pages.Pages;

@Model
public class NavigationManager {

    public Class<? extends ViewConfig> goToHome(){
        return Pages.Home.class;
    }

    public Class<? extends ViewConfig> goToMyTasks(){
        return Pages.Protected.MyTasks.class;
    }

    public Class<? extends ViewConfig> goToLogin(){
        return Pages.Login.class;
    }

    public Class<? extends ViewConfig> goToCreateTask(){
        return Pages.Protected.Admin.CreateTask.class;
    }

    public Class<? extends ViewConfig> goToWebLog(){
        return Pages.Protected.Admin.WebLog.class;
    }
}
