package org.jboss.examples.deltaspike.expensetracker.app.extension;

import java.io.Serializable;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

/**
 * Supports declarative conversation demarcation, like in Seam, with slightly
 * different semantics and much more trivially.
 */
@Interceptor
@ConversationControlAnnotated
public class ConversationInterceptor implements Serializable {

    @Inject
    private Conversation conv;

    @Inject
    private InitiatorHolder holder;

    @AroundInvoke
    public Object startOrEndConversation(InvocationContext ctx) throws Exception {
        Begin begin = ctx.getMethod().getAnnotation(Begin.class);
        End end = ctx.getMethod().getAnnotation(End.class);

        if (begin != null && end != null) {
            // nothing, invalid combination
            // log error
        }

        if (begin != null) {
            if (conv.isTransient() || begin.force()) {
                if (begin.force()) {
                    if (!conv.isTransient()) {
                        conv.end();
                    }
                }
                conv.begin();
                holder.setConversationInitiator(ctx.getMethod().getDeclaringClass());
            }
            return ctx.proceed();
        }

        if (end != null) {
            Object result = ctx.proceed();
            if (ctx.getMethod().getDeclaringClass().equals(holder.getConversationInitiator())) {
                if (!conv.isTransient()) {
                    conv.end();
                }
            }
            return result;
        }

        return ctx.proceed();
    }

    @ConversationScoped
    public static class InitiatorHolder implements Serializable {

        private Class<?> conversationInitiator;

        public Class<?> getConversationInitiator() {
            return conversationInitiator;
        }

        public void setConversationInitiator(Class<?> conversationInitiator) {
            this.conversationInitiator = conversationInitiator;
        }

    }

}
