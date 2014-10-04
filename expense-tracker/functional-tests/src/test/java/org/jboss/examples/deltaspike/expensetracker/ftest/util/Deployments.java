package org.jboss.examples.deltaspike.expensetracker.ftest.util;

import java.io.File;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;

public class Deployments {

    private static final String TEST_DEPLOYMENT_PROPERTY = "testDeployment";
    private static final String TEST_DEPLOYMENT = System.getProperty(TEST_DEPLOYMENT_PROPERTY);

    public static WebArchive defaultDeployment() {
        return ShrinkWrap.createFromZipFile(WebArchive.class, new File(TEST_DEPLOYMENT));
    }

    /*
     * Sets the project stage to IntegrationTest, changes dates to timestamps.
     */
    public static JavaArchive testTools() {
        return ShrinkWrap.create(JavaArchive.class, "test-tools.jar")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsManifestResource("apache-deltaspike.properties")
                .addClass(TimestampConverter.class)
                .addClass(TestCurrencyConverter.class);
    }
}
