package org.jboss.examples.deltaspike.mbeantranslator.server.messages;

import org.jboss.examples.deltaspike.mbeantranslator.common.Language;
import javax.inject.Inject;
import java.util.Locale;
import org.apache.deltaspike.core.api.message.LocaleResolver;

/**
 * @author Tomas Remes
 */
@Custom
public class CustomLocaleResolver implements LocaleResolver {

    @Inject
    Language language;

    @Override
    public Locale getLocale() {
        return language.equals(Language.GERMAN) ? Locale.GERMAN : Locale.FRENCH;
    }
}
