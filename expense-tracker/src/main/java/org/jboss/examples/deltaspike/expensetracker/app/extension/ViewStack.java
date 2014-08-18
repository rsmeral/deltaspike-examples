package org.jboss.examples.deltaspike.expensetracker.app.extension;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.Deque;
import javax.inject.Inject;
import org.apache.deltaspike.core.api.config.view.ViewConfig;
import org.apache.deltaspike.core.api.config.view.metadata.ViewConfigResolver;
import org.apache.deltaspike.core.api.scope.WindowScoped;
import org.jboss.examples.deltaspike.expensetracker.view.Pages;

@WindowScoped
public class ViewStack implements Serializable {

    private static final int DEPTH = 10;

    private static final Class<? extends ViewConfig> DEFAULT_VIEW = Pages.Secured.Home.class;

    private final Deque<Class<? extends ViewConfig>> viewStack;

    @Inject
    private ViewConfigResolver viewConfigResolver;

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
        viewStack.addFirst(view);
    }

}
