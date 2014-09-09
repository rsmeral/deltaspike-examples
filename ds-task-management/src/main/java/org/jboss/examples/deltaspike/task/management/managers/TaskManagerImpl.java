package org.jboss.examples.deltaspike.task.management.managers;

import java.util.Date;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

import org.apache.deltaspike.core.api.config.view.ViewConfig;
import org.jboss.examples.deltaspike.task.management.domain.Task;
import org.jboss.examples.deltaspike.task.management.pages.Pages;
import org.jboss.examples.deltaspike.task.management.repositories.TaskRepository;

@Model
public class TaskManagerImpl {

    @Inject
    private Task task;

    @Inject
    private TaskRepository taskRepository;

    public Class<? extends ViewConfig> createTask() {
        if (task.getName() != null && task.getUser().getLogin() != null && task.getDeadline() != null
            && task.getDescription() != null) {

            taskRepository.save(task);
            return Pages.Protected.Admin.TaskCreated.class;
        } else {
            return null;
        }
    }

    public void finishTask(Task t) {
        t.setFinishDate(new Date());
        taskRepository.save(t);
        Task findBy = taskRepository.findBy(t.getId());
    }
}
