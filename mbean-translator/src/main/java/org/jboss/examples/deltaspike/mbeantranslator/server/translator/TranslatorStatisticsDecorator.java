package org.jboss.examples.deltaspike.mbeantranslator.server.translator;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.inject.Inject;
import org.jboss.examples.deltaspike.mbeantranslator.common.Language;

@Decorator
public abstract class TranslatorStatisticsDecorator implements Translator {

    @Inject
    @Delegate
    private TranslatorService translator;

    @Inject
    private TranslationStatistics statistics;

    @Inject
    private Language lang;

    @Override
    public String translate(String messageText) {
        statistics.addTranslation(lang);

        return translator.translate(messageText);
    }

}
