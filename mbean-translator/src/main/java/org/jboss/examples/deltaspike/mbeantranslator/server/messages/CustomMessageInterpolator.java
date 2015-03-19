package org.jboss.examples.deltaspike.mbeantranslator.server.messages;

import javax.inject.Inject;
import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Locale;
import org.apache.deltaspike.core.api.message.MessageInterpolator;

/**
 * @author Tomas Remes
 */
@Custom
public class CustomMessageInterpolator implements MessageInterpolator {

    @Inject
    String originalText;

    @Override
    public String interpolate(String messageText, Serializable[] arguments, Locale locale) {

        // if messageText contains original text, then it's likely not translated.
        if (messageText.contains(originalText)) {
            return "";
        } else {
            return MessageFormat.format(messageText, (Object[]) arguments);
        }
    }
}
