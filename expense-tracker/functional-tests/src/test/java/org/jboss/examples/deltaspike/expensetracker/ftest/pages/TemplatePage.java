package org.jboss.examples.deltaspike.expensetracker.ftest.pages;

import org.jboss.examples.deltaspike.expensetracker.ftest.fragments.MainToolbar;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.richfaces.fragment.messages.RichFacesMessages;
import org.richfaces.fragment.messages.RichFacesMessages.MessageImpl;

public class TemplatePage {

    @FindBy(id = "toolbarForm:toolbar")
    private MainToolbar toolbar;

    @FindBy(tagName = "h1")
    private WebElement heading;

    @FindBy(id = "globalMessages")
    private RichFacesMessages globalMessages;

    @FindBy(id = "toolbarForm:currentUserName")
    private WebElement currentUserName;

    public String getHeadingText() {
        return heading.getText();
    }

    public MainToolbar getToolbar() {
        return toolbar;
    }

    public RichFacesMessages getGlobalMessages() {
        return globalMessages;
    }

    public String getCurrentUserName() {
        return currentUserName.getAttribute("value");
    }

    public boolean isGlobalMessagePresent(String messageText) {
        for (MessageImpl msg : globalMessages.getItems()) {
            if (msg.getText().trim().equals(messageText)) {
                return true;
            }
        }
        return false;
    }
}
