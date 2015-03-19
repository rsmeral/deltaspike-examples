package org.jboss.examples.deltaspike.periodicstats.test;

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
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.Assert.*;

@RunAsClient
@RunWith(Arquillian.class)
public class PeriodicStatsTest {

    @FindBy(id = "messageForm:message")
    private WebElement MESSAGE;

    @FindBy(id = "messageForm:send")
    private WebElement SEND;

    @FindBy(id = "statsForm:start")
    private WebElement START;

    @FindBy(id = "statsForm:stop")
    private WebElement STOP;

    @FindBy(id = "statsForm:refresh")
    private WebElement REFRESH;

    @FindBy(id = "statsForm:clear")
    private WebElement CLEAR;

    @FindBy(id = "count")
    private WebElement MESSAGE_COUNT;

    @FindBy(id = "message-word-count")
    private WebElement AVG_WORD_COUNT;

    @FindBy(id = "inter-arrival-time")
    private WebElement AVG_INTER_ARRIVAL_TIME;

    @Drone
    private WebDriver driver;

    @ArquillianResource
    private URL contextPath;

    private static final String TEST_DEPLOYMENT_PROPERTY = "testDeployment";
    private static final String TEST_DEPLOYMENT = System.getProperty(TEST_DEPLOYMENT_PROPERTY);

    private static final String MESSAGE_ONE_WORD = "singularity";
    private static final String MESSAGE_TWO_WORDS = "singularity will";
    private static final String MESSAGE_THREE_WORDS = "singularity will overtake";

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
    public void testMessenger() {
        // send one message
        send(MESSAGE_ONE_WORD);
        assertStats(1, 1);

        // send a message while stopped
        STOP.click();
        send(MESSAGE_THREE_WORDS);
        assertStats(1, 1);

        // restart, stats should be updated
        START.click();
        assertStats(2, 2);

        // test clearing
        CLEAR.click();
        assertStats(0, 0);
        send(MESSAGE_TWO_WORDS);
        assertStats(1, 2);
    }

    private void assertStats(int expectedMsgCount, int expectedWordCount) {
        sleep(1000);
        REFRESH.click();
        assertEquals(expectedMsgCount, Integer.parseInt(MESSAGE_COUNT.getText()));
        assertEquals(expectedWordCount, (int) Double.parseDouble(AVG_WORD_COUNT.getText()));
    }

    private void send(String text) {
        MESSAGE.clear();
        MESSAGE.sendKeys(text);
        SEND.click();
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ex) {
            Logger.getLogger(PeriodicStatsTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
