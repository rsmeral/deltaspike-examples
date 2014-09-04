package org.jboss.examples.deltaspike.expensetracker.ftest.fragments;

import org.jboss.arquillian.graphene.GrapheneElement;
import org.jboss.arquillian.graphene.findby.FindByJQuery;
import org.jboss.arquillian.graphene.fragment.Root;
import org.openqa.selenium.WebElement;
import org.richfaces.fragment.message.RichFacesMessage;
import org.richfaces.fragment.messages.RichFacesMessages;

public class PasswordForm {

    @Root
    private GrapheneElement root;

    @FindByJQuery("[id$='password']")
    private WebElement passwordInput;

    @FindByJQuery("[id$='passwordMessage']")
    private RichFacesMessage passwordMsg;

    @FindByJQuery("[id$='passwordConfirmation']")
    private WebElement passwordConfirmationInput;

    @FindByJQuery("[id$='changePasswordBtn']")
    private GrapheneElement changePasswordBtn;

    @FindByJQuery("[id$='passwordValidatorMessages']")
    private RichFacesMessages validatorMessages;

    public void changePassword(String password, String confirmation) {
        setPassword(password, confirmation);
        changePasswordBtn.click();
    }

    public void setPassword(String password, String confirmation) {
        passwordInput.clear();
        passwordInput.sendKeys(password);
        passwordConfirmationInput.clear();
        passwordConfirmationInput.sendKeys(confirmation);
    }

    public RichFacesMessage getPasswordMessage() {
        return passwordMsg;
    }

    public RichFacesMessages getValidatorMessages() {
        return validatorMessages;
    }
    
    public boolean isPresent() {
        return root.isPresent();
    }
}
