package org.jboss.examples.deltaspike.dspasswd.ftest;

import java.io.File;
import java.net.URL;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

@RunWith(Arquillian.class)
public class MBeanTranslatorTest {

    private static final String TEST_DEPLOYMENT_PROPERTY = "testDeployment";
    private static final String TEST_DEPLOYMENT = System.getProperty(TEST_DEPLOYMENT_PROPERTY);

    private static final String GERMAN = "German";
    private static final String FRENCH = "French";

    private static final String HELLO = "hello";
    private static final String HELLO_GERMAN = "Hallo";

    private static final String THANKS = "thanks";
    private static final String THANKS_FRENCH = "Merci";

    @Drone
    private WebDriver driver;

    @ArquillianResource
    private URL contextPath;

    @Deployment(testable = false)
    public static WebArchive deployment() {
        return ShrinkWrap.createFromZipFile(WebArchive.class, new File(TEST_DEPLOYMENT));
    }

    @FindBy(id = "inputForm:language")
    private WebElement languageSelect;

    @FindBy(id = "inputForm:text")
    private WebElement text;

    @FindBy(id = "inputForm:translate")
    private WebElement translateBtn;

    @FindBy(id = "statisticsForm:allTranslations")
    private WebElement allTranslations;

    @FindBy(id = "statisticsForm:germanTranslations")
    private WebElement germanTranslationsPercentage;

    @FindBy(id = "statisticsForm:frenchTranslations")
    private WebElement frenchTranslationsPercentage;

    @FindBy(id = "statisticsForm:reset")
    private WebElement resetBtn;

    @FindBy(id = "translation")
    private WebElement translation;

    @Test
    public void test() {
        driver.get(contextPath.toString());

        new Select(languageSelect).selectByVisibleText(GERMAN);
        text.clear();
        text.sendKeys(HELLO);
        translateBtn.click();

        Assert.assertEquals(HELLO_GERMAN, translation.getText());
        Assert.assertEquals(1, Integer.parseInt(allTranslations.getText()));
        Assert.assertEquals(100, Integer.parseInt(germanTranslationsPercentage.getText()));
        Assert.assertEquals(0, Integer.parseInt(frenchTranslationsPercentage.getText()));

        new Select(languageSelect).selectByVisibleText(FRENCH);
        text.clear();
        text.sendKeys(THANKS);
        translateBtn.click();

        Assert.assertEquals(THANKS_FRENCH, translation.getText());
        Assert.assertEquals(2, Integer.parseInt(allTranslations.getText()));
        Assert.assertEquals(50, Integer.parseInt(germanTranslationsPercentage.getText()));
        Assert.assertEquals(50, Integer.parseInt(frenchTranslationsPercentage.getText()));

        resetBtn.click();
        
        Assert.assertEquals(0, Integer.parseInt(allTranslations.getText()));
        Assert.assertEquals(0, Integer.parseInt(germanTranslationsPercentage.getText()));
        Assert.assertEquals(0, Integer.parseInt(frenchTranslationsPercentage.getText()));
    }

}
