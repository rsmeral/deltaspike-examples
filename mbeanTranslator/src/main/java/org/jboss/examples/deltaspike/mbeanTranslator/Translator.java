package org.jboss.examples.deltaspike.mbeanTranslator;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import org.apache.deltaspike.core.api.message.Message;
import org.apache.deltaspike.core.api.message.MessageContext;
import org.jboss.examples.deltaspike.mbeanTranslator.messages.Custom;
import org.jboss.examples.deltaspike.mbeanTranslator.messages.CustomLocaleResolver;
import org.jboss.examples.deltaspike.mbeanTranslator.messages.CustomMessageInterpolator;
import org.jboss.examples.deltaspike.mbeanTranslator.messages.InternationalMessages;

/**
 * @author Tomas Remes
 */
@RequestScoped
public class Translator {

    private final String MESSAGE_SOURCE = "org.jboss.examples.deltaspike.mbeanTranslator.messages.InternationalMessages";

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

    public String translate(String messageText) {

        Message message = messageContext.messageSource(MESSAGE_SOURCE)
                .messageInterpolator(interpolator)
                .localeResolver(localeResolver).message();

        message = message.template("{" + messageText.toLowerCase().trim() + "}");

        if (message.toString() != "") {
            return message.toString();
        } else {
            return this.messages.unknownText();
        }

    }
}
