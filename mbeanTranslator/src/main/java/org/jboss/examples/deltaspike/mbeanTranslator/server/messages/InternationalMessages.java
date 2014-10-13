package org.jboss.examples.deltaspike.mbeanTranslator.server.messages;

import org.apache.deltaspike.core.api.message.MessageBundle;
import org.apache.deltaspike.core.api.message.MessageTemplate;

/**
 * @author Tomas Remes
 */
@MessageBundle
public interface InternationalMessages {

    @MessageTemplate(value="{unknown}")
    String unknownText();
}
