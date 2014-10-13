package org.jboss.examples.deltaspike.dspasswd.ftest;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.graphene.findby.ByJQuery;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/*
 * Tests InjectableResource with classpath provider. The file provider can be
 * verified manually.
 */
@RunWith(Arquillian.class)
public class DsPasswdTest {

    private static final String TEST_DEPLOYMENT_PROPERTY = "testDeployment";
    private static final String TEST_DEPLOYMENT = System.getProperty(TEST_DEPLOYMENT_PROPERTY);

    @Drone
    private WebDriver driver;

    @ArquillianResource
    private URL contextPath;

    @Deployment(testable = false)
    public static WebArchive deployment() {
        return ShrinkWrap.createFromZipFile(WebArchive.class, new File(TEST_DEPLOYMENT))
                .addAsLibrary(testTools());
    }

    @FindBy(css = "#usersTable tbody")
    private WebElement usersTable;

    private static JavaArchive testTools() {
        return ShrinkWrap.create(JavaArchive.class, "test-tools.jar")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsManifestResource("apache-deltaspike.properties")
                .addClass(TestPasswdProvider.class);
    }

    private final List<String[]> expectedList = new ArrayList<String[]>() {
        {
            add(new String[]{"deltaspike", "1011", "1010", "DeltaSpike user", "/home/deltaspike", "/bin/bash"});
            add(new String[]{"duke", "1012", "1010", "Java Mascot", "/home/duke", "/bin/bash"});
            add(new String[]{"cdi", "1021", "1020", "CDI user", "/home/cdi", "/bin/bash"});
        }
    };

    private String cell(WebElement cell, String id) {
        return cell.findElement(ByJQuery.selector("[id$='" + id + "']")).getText();
    }

    @Test
    public void test() {
        driver.get(contextPath.toString());
        
        List<WebElement> rows = usersTable.findElements(By.tagName("tr"));
        
        Assert.assertEquals(3, rows.size());

        for (int i = 0; i < rows.size(); i++) {
            WebElement r = rows.get(i);
            String[] foundRow = new String[]{
                cell(r, "name"),
                cell(r, "uid"),
                cell(r, "gid"),
                cell(r, "desc"),
                cell(r, "home"),
                cell(r, "shell")};
            Assert.assertArrayEquals(expectedList.get(i), foundRow);
        }

    }

}
