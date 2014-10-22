package org.jboss.examples.deltaspike.task.management.test;

public enum Page {

    HOME("/pages/home.xhtml"), MY_TASKS("/pages/protected/my_tasks.xhtml"), CREATE_TASK(
        "/pages/protected/admin/create_task.xhtml"), WEB_LOG("/pages/protected/admin/web_log.xhtml"), ACCESS_DENIED(
        "/pages/access_denied.xhtml"), TASK_CREATED("/pages/protected/admin/task_created.xhtml");

    private String path;

    private Page(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    @Override
    public String toString() {
        return path;
    }
}
