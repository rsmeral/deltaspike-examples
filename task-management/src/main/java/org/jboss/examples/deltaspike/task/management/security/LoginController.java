package org.jboss.examples.deltaspike.task.management.security;

import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.apache.deltaspike.core.api.config.view.ViewConfig;
import org.jboss.examples.deltaspike.task.management.domain.User;
import org.jboss.examples.deltaspike.task.management.pages.Pages;
import org.jboss.examples.deltaspike.task.management.repositories.UserRepository;

@Model
public class LoginController {

    @Inject
    private User user;

    @Inject
    private UserRepository userRepository;

    @Inject
    private LoginCredentials loginCredentials;

    @Inject
    private FacesContext faces;

    public Class<? extends ViewConfig> login() {
        User foundUser = userRepository.authorizeUser(loginCredentials.getLogin(), loginCredentials.getPassword());
        if (foundUser == null) {
            faces
                .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Wrong username or password, try again", null));
            return null;
        }

        user.setAdministator(foundUser.isAdministator());
        user.setLogin(foundUser.getLogin());
        user.setName(foundUser.getName());
        user.setSurname(foundUser.getSurname());

        return Pages.Protected.Home.class;
    }

    public Class<? extends ViewConfig> logout() {
        user.setAdministator(false);
        user.setLogin(null);
        user.setName(null);
        user.setSurname(null);
        return Pages.Login.class;
    }

    public boolean isUserLoggedIn() {
        return user != null && user.getLogin() != null;
    }

}
