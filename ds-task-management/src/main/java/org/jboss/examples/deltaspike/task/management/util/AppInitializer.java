package org.jboss.examples.deltaspike.task.management.util;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

import org.apache.deltaspike.core.api.config.view.ViewRef;
import org.apache.deltaspike.core.api.config.view.controller.InitView;
import org.jboss.examples.deltaspike.task.management.domain.Task;
import org.jboss.examples.deltaspike.task.management.domain.User;
import org.jboss.examples.deltaspike.task.management.pages.Pages;
import org.jboss.examples.deltaspike.task.management.repositories.TaskRepository;
import org.jboss.examples.deltaspike.task.management.repositories.UserRepository;

@Model
@ViewRef(config=Pages.Login.class)
public class AppInitializer implements Serializable {

    private static final long serialVersionUID = 7671809179577471311L;

    @Inject
    private UserRepository userRepository;

    @Inject
    private TaskRepository taskRepository;

    private static boolean initialized = false;

    public AppInitializer() {
    }

    @InitView
    public void initialize() {
        if (initialized) {
            return;
        }
        System.err.println("initialized!!!!!!!!!!");

        final Calendar calendar = Calendar.getInstance();
        calendar.set(2015, 3, 10, 8, 0);

        final User admin = new User("admin", "Admin", "Adminovic", true, "admin123");
        userRepository.save(admin);

        taskRepository.save(new Task("Create user", "create new dummy user with name John", admin, calendar.getTime(), null));
        calendar.set(2015, 4, 10, 8, 0);
        taskRepository.save(new Task("Delete user", "try to delete any user", admin, calendar.getTime(), null));
        calendar.set(2015, 3, 10, 8, 0);
        taskRepository.save(new Task("Login", "try to login into this system", admin, calendar.getTime(), new Date()));

        User user = new User("user", "User", "Userovic", false, "user123");
        userRepository.save(user);

        calendar.set(2015, 1, 2, 8, 0);
        taskRepository.save(new Task("Login", "Try to login into the system and look at all options", user, calendar.getTime(),
            null));
        calendar.set(2015, 2, 20, 8, 0);
        taskRepository.save(new Task("Hack the system", "try to create new task - this should not be possible", user, calendar
            .getTime(), null));

        initialized = true;
    }

    public static boolean isInitialized() {
        return initialized;
    }
}