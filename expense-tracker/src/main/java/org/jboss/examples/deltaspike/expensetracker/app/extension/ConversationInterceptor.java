package org.jboss.examples.deltaspike.expensetracker.app.extension;

import java.io.Serializable;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import org.apache.deltaspike.core.api.config.view.ViewConfig;
import org.apache.deltaspike.core.api.scope.ConversationGroup;
import org.apache.deltaspike.core.api.scope.GroupedConversationScoped;
import org.apache.deltaspike.core.spi.scope.conversation.GroupedConversationManager;

/**
 * Supports declarative conversation demarcation, like in Seam, with slightly
 * different semantics and much more trivially.
 */
@Interceptor
@ConversationControlAnnotated
public class ConversationInterceptor implements Serializable {

    @Inject
    private GroupedConversationManager convMgr;

    @Inject
    @ConversationGroup(Controller.class)
    private InitiatorHolder holder;

    @Inject
    @CurrentView
    private Instance<Class> currentView;

    @AroundInvoke
    public Object startOrEndConversation(InvocationContext ctx) throws Exception {
        Begin begin = ctx.getMethod().getAnnotation(Begin.class);
        End end = ctx.getMethod().getAnnotation(End.class);

        if (begin != null && end != null) {
            // nothing, invalid combination
            // TODO: should log error
        }

        if (begin != null) {
            return conditionallyBeginConversation(ctx, begin);
        }

        if (end != null) {
            return conditionallyEndConversation(ctx, end);
        }

        return ctx.proceed();
    }

    private Object conditionallyEndConversation(InvocationContext ctx, End end) throws Exception {
        Object result = ctx.proceed();
        Class<? extends ViewConfig> returningTo = ((Class<? extends ViewConfig>) result);
        if (returningTo.equals(holder.getConversationInitiator())) {
            convMgr.closeConversationGroup(Controller.class);
//            if (!conv.isTransient()) {
//                conv.end();
//            }
        }
        return result;
    }

    private Object conditionallyBeginConversation(InvocationContext ctx, Begin begin) throws Exception {
        if (holder.getConversationInitiator() == null) {
//        if (conv.isTransient() || begin.force()) {
//            if (begin.force()) {
            // not sure if this works
//                if (!conv.isTransient()) {
//                    conv.end();
//                }
//            }
//            conv.begin();
            holder.setConversationInitiator(currentView.get());
        }
        return ctx.proceed();
    }

    /*
     * Conversation initiator is the view that is current at the time of 
     * interception.
     */
    @GroupedConversationScoped
    @ConversationGroup(Controller.class)
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
