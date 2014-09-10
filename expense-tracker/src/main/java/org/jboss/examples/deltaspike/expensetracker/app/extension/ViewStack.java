package org.jboss.examples.deltaspike.expensetracker.app.extension;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.Deque;
import org.apache.deltaspike.core.api.config.view.ViewConfig;
import org.apache.deltaspike.core.api.scope.WindowScoped;
import org.jboss.examples.deltaspike.expensetracker.view.SecuredPages;

/**
 * Simplifies a "back" or "cancel" operation in views. Every visited view
 * annotated with {@link ViewStacked} is pushed on to the stack, which can then
 * be traversed back with a series of "back" operations, which call
 * {@link #pop()}. Only the view configs are kept, not view states.
 *
 * This might be useful for universal views which are used in multiple scenarios
 * and to which a user might arrive through different paths.
 *
 * The stack has a fixed depth and works in a LRU fashion.
 */
@WindowScoped
public class ViewStack implements Serializable {

    private static final int DEPTH = 10;

    private static final Class<? extends ViewConfig> DEFAULT_VIEW = SecuredPages.Home.class;

    private final Deque<Class<? extends ViewConfig>> viewStack;

    public ViewStack() {
        viewStack = new ArrayDeque<Class<? extends ViewConfig>>(DEPTH);
    }

    private Class<? extends ViewConfig> popStack() {
        if (viewStack.isEmpty()) {
            return DEFAULT_VIEW;
        }
        return viewStack.removeFirst();
    }

    public Class<? extends ViewConfig> pop() {
        // ditch the top, it's the "current" view
        popStack();
        return popStack();
    }

    public void push(Class<? extends ViewConfig> view) {
        if (viewStack.size() == DEPTH) {
            viewStack.removeLast();
        }
        if (!view.equals(viewStack.peekFirst())) {
            viewStack.addFirst(view);
        }
    }

}
