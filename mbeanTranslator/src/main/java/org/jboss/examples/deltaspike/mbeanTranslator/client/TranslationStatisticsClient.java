package org.jboss.examples.deltaspike.mbeanTranslator.client;

import java.lang.management.ManagementFactory;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.management.*;
import org.jboss.examples.deltaspike.mbeanTranslator.server.translator.TranslationStatistics;

/**
 * A client for the TranslationStatistics MBean.
 * 
 * @author rsmeral
 */
@Named
@ApplicationScoped
public class TranslationStatisticsClient {

    private final MBeanServer server = ManagementFactory.getPlatformMBeanServer();
    private ObjectName translationStatisticsMBeanName = null;

    public TranslationStatisticsClient() {
        try {
            translationStatisticsMBeanName = new ObjectName("org.apache.deltaspike:type=MBeans,name=" + TranslationStatistics.class.getName());
        } catch (MalformedObjectNameException ex) {
            Logger.getLogger(TranslationStatisticsClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Object getAllTranslations() {
        return invokeOperation("getTranslations");
    }

    public Object getGermanTranslationsPercentage() {
        return invokeOperation("getGermanTranslationsPercentage");
    }

    public Object getFrenchTranslationsPercentage() {
        return invokeOperation("getFrenchTranslationsPercentage");
    }

    public void reset() {
        invokeOperation("reset");
    }

    private Object invokeOperation(String operation) {
        try {
            Object result = server.invoke(translationStatisticsMBeanName, operation, null, null);
            return result;
        } catch (Exception ex) {
            Logger.getLogger(TranslationStatisticsClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
