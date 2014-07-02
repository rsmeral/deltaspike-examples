package org.jboss.examples.deltaspike.mbeanTranslator;

import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import org.jboss.examples.deltaspike.mbeanTranslator.messages.Language;

/**
 * @author Tomas Remes
 */
@Model
public class ViewController {

    private static MBeanServer server = ManagementFactory.getPlatformMBeanServer();

    @Inject
    TranslationStatisticsMBean bean;

    private Language[] languages = Language.values();
    private Language language = Language.GERMAN;
    private String inputText;
    private String translatedText;

    public Language[] getLanguages() {
        return languages;
    }

    public void setLanguages(Language[] languages) {
        this.languages = languages;
    }

    @Produces
    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    @Produces
    public String getInputText() {
        return inputText;
    }

    public void setInputText(String inputText) {
        this.inputText = inputText;
    }

    public String getTranslatedText() {
        return translatedText;
    }

    public void setTranslatedText(String translatedText) {
        this.translatedText = translatedText;
    }

    public void translate() {
        if (!inputText.equals("")) {
            translatedText = bean.passToTranslator(inputText, language);
        }
    }

    public Object getAllTranslations() throws Exception {
        Object tran = server.invoke(getObjectName(), "getTranslations", null, null);
        return tran;
    }

    public Object getGermanTranslationsPercentage() throws Exception {
        Object tran = server.invoke(getObjectName(), "getGermanTranslationsPercentage", null, null);
        return tran;
    }

    public Object getFrenchTranslationsPercentage() throws Exception {
        Object tran = server.invoke(getObjectName(), "getFrenchTranslationsPercentage", null, null);
        return tran;
    }

    private ObjectName getObjectName() throws Exception {
        return new ObjectName("org.apache.deltaspike:type=MBeans,name=" + TranslationStatisticsMBean.class.getName());
    }

    public void reset() {
        bean.reset();
    }

}
