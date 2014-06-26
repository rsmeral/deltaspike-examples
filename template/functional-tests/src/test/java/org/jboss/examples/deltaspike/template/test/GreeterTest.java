package org.jboss.examples.deltaspike.template.test;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.io.File;
import java.net.URL;
import static org.jboss.arquillian.graphene.Graphene.*;
import org.jboss.arquillian.graphene.findby.FindByJQuery;
import static org.junit.Assert.*;

@RunAsClient
@RunWith(Arquillian.class)
public class GreeterTest {

    @FindBy(id = "message")
    WebElement MESSAGE;

    @FindByJQuery("input[id *= 'greeting']")
    WebElement GREETING;

    @FindByJQuery("input[id *= 'name']")
    WebElement NAME;

    @FindByJQuery("input[value = 'Submit']")
    WebElement SUBMIT;

    @Drone
    WebDriver driver;

    @ArquillianResource
    URL contextPath;

    private static final String EXPECTED_MSG = "DeltaSpike says 'Hello, World!'";
    private static final String IN_GREETING = "Hello";
    private static final String IN_NAME = "World";
    
    private static final String TEST_DEPLOYMENT = "../target/ds-template.war";

    @Deployment(testable = false)
    public static WebArchive deployment() {
        return ShrinkWrap.createFromZipFile(WebArchive.class, new File(TEST_DEPLOYMENT));
    }

    @Before
    public void beforeTest() {
        driver.get(contextPath.toString());
    }

    @Test
    @InSequence(1)
    public void testGreeting() {
        GREETING.sendKeys(IN_GREETING);
        NAME.sendKeys(IN_NAME);
        guardHttp(SUBMIT).click();
        
        assertEquals(EXPECTED_MSG, MESSAGE.getText().trim());
    }

}

