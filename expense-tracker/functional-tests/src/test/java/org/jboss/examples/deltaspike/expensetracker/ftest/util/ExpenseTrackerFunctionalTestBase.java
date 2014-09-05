package org.jboss.examples.deltaspike.expensetracker.ftest.util;

import org.jboss.examples.deltaspike.expensetracker.ftest.util.Deployments;
import java.net.URL;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.examples.deltaspike.expensetracker.ftest.pages.HomePage;
import org.jboss.examples.deltaspike.expensetracker.ftest.pages.LoginPage;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.openqa.selenium.WebDriver;
import static org.jboss.examples.deltaspike.expensetracker.ftest.util.TestConstants.*;

public abstract class ExpenseTrackerFunctionalTestBase {

    @Drone
    private WebDriver driver;

    @ArquillianResource
    private URL contextPath;

    @Deployment(testable = false)
    public static WebArchive deployment() {
        return Deployments.defaultDeployment()
                .addAsLibrary(Deployments.testTools());

    }

    public String getLocation() {
        return driver.getCurrentUrl();
    }

    public WebDriver getDriver() {
        return driver;
    }

    public URL getContextPath() {
        return contextPath;
    }

    /*
     * Convenience method, logs out if logged in as a different user and logs in
     */
    public void login(String username, String password) {
        HomePage homePage = Graphene.goTo(HomePage.class);
        if (getLocation().contains(PAGE_HOME)) {
            if (homePage.getCurrentUserName().equals(username)) {
                return;
            } else {
                homePage.getToolbar().logout();
            }
        }

        LoginPage loginPage = Graphene.goTo(LoginPage.class);
        loginPage.login(username, password);
    }
}
