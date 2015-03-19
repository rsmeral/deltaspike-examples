package org.jboss.examples.deltaspike.task.management.managers;

import java.util.List;

import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import org.apache.deltaspike.core.api.config.view.navigation.ViewNavigationHandler;
import org.apache.deltaspike.data.api.audit.CurrentUser;
import org.jboss.examples.deltaspike.task.management.domain.Task;
import org.jboss.examples.deltaspike.task.management.domain.User;
import org.jboss.examples.deltaspike.task.management.pages.Pages;
import org.jboss.examples.deltaspike.task.management.repositories.UserNotMappedRepository;
import org.jboss.examples.deltaspike.task.management.repositories.UserTaskMappedRepository;
import org.jboss.examples.deltaspike.task.management.security.LoginController;

@Model
public class UserManagerImpl {

    @Inject
    private UserNotMappedRepository userNotMappedRepository;

    @Inject
    private UserTaskMappedRepository userTaskRepository;

    @Inject
    private User user;

    @Inject
    private LoginController loginController;

    @Inject
    private ViewNavigationHandler navigationHandler;

    public List<Task> getAllTasks() {
        if (!loginController.isUserLoggedIn()) {
            navigationHandler.navigateTo(Pages.Login.class);
            return null;
        }

        List<Task> allTasks = userTaskRepository.getAllTasks(user.getLogin());

        return allTasks;
    }

    public List<String> getAllUsersLogin() {
        return userNotMappedRepository.getAllUsersLogin();
    }


    @Produces
    @CurrentUser
    public String currentUser() {
        return user.getLogin() + String.format(" (%s %s)", user.getName(), user.getSurname());
    }
}
