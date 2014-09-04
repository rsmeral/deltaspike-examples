package org.jboss.examples.deltaspike.expensetracker.ftest;

import java.io.File;
import java.net.URL;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.examples.deltaspike.expensetracker.ftest.pages.HomePage;
import org.jboss.examples.deltaspike.expensetracker.ftest.pages.LoginPage;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.openqa.selenium.WebDriver;
import static org.jboss.examples.deltaspike.expensetracker.ftest.TestConstants.*;

public abstract class ExpenseTrackerFunctionalTestBase {

    private static final String TEST_DEPLOYMENT_PROPERTY = "testDeployment";

    @Drone
    private WebDriver driver;

    @ArquillianResource
    private URL contextPath;

    private static final String TEST_DEPLOYMENT = System.getProperty(TEST_DEPLOYMENT_PROPERTY);

    @Deployment(testable = false)
    public static WebArchive deployment() {
        return ShrinkWrap.createFromZipFile(WebArchive.class, new File(TEST_DEPLOYMENT));
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
