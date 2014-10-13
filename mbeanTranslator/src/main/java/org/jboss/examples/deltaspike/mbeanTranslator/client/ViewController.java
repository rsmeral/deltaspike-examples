package org.jboss.examples.deltaspike.mbeanTranslator.client;

import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import org.jboss.examples.deltaspike.mbeanTranslator.server.translator.Translator;
import org.jboss.examples.deltaspike.mbeanTranslator.common.Language;

/**
 * @author Tomas Remes
 */
@Model
public class ViewController {

    @Inject
    private Translator translator;
    
    @Inject 
    private TranslationStatisticsClient client;

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
        if (!"".equals(inputText)) {
            translatedText = translator.translate(inputText);
        }
    }

    public Object getAllTranslations() {
        return client.getAllTranslations();
    }

    public Object getGermanTranslationsPercentage() {
        return client.getGermanTranslationsPercentage();
    }

    public Object getFrenchTranslationsPercentage() {
        return client.getFrenchTranslationsPercentage();
    }

    public void reset() {
        client.reset();
    }

}
