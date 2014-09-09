package org.jboss.examples.deltaspike.task.management.security;

import javax.enterprise.inject.Model;

@Model
public class LoginCredentials {

    // private String password = "admin123";
    // private String login = "admin";

    private String password = "user123";
    private String login = "user";

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        if (login == null || "".equals(login)) {
            throw new IllegalArgumentException("User login can not be empty.");
        }

        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        if (password == null || "".equals(password)) {
            throw new IllegalArgumentException("Password can not be empty.");
        }

        this.password = password;
    }

    public void invalidate() {
        this.password = null;
        this.login = null;
    }

}
