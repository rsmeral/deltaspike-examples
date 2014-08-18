package org.jboss.examples.deltaspike.expensetracker.app.extension;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import org.apache.deltaspike.core.api.scope.GroupedConversation;

@Interceptor
@ConversationControlAnnotated
public class ConversationInterceptor {

    @Inject
    private GroupedConversation conv;

    @AroundInvoke
    public Object startOrEndConversation(InvocationContext ctx) throws Exception {
        End end = ctx.getMethod().getAnnotation(End.class);

        if (end != null) {
            Object result = ctx.proceed();
            conv.close();
            return result;
        }

        return ctx.proceed();
    }

}
