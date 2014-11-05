package org.jboss.examples.deltaspike.mbeantranslator.server.translator;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import org.apache.deltaspike.core.api.message.Message;
import org.apache.deltaspike.core.api.message.MessageContext;
import org.jboss.examples.deltaspike.mbeantranslator.server.messages.Custom;
import org.jboss.examples.deltaspike.mbeantranslator.server.messages.CustomMessageInterpolator;
import org.jboss.examples.deltaspike.mbeantranslator.server.messages.CustomLocaleResolver;
import org.jboss.examples.deltaspike.mbeantranslator.server.messages.InternationalMessages;

/**
 * @author Tomas Remes
 */
@RequestScoped
public class TranslatorService implements Translator {

    private final String MESSAGE_SOURCE = "org.jboss.examples.deltaspike.mbeanTranslator.server.messages.InternationalMessages";

    @Inject
    MessageContext messageContext;

    @Inject
    InternationalMessages messages;

    @Inject
    @Custom
    CustomLocaleResolver localeResolver;

    @Inject
    @Custom
    CustomMessageInterpolator interpolator;

    @Override
    public String translate(String messageText) {

        Message message = messageContext.messageSource(MESSAGE_SOURCE)
                .messageInterpolator(interpolator)
                .localeResolver(localeResolver).message();

        message = message.template("{" + messageText.toLowerCase().trim() + "}");

        if (!"".equals(message.toString())) {
            return message.toString();
        } else {
            return this.messages.unknownText();
        }

    }
}
