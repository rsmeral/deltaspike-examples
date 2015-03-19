package org.jboss.examples.deltaspike.task.management.pages;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.apache.deltaspike.core.api.config.view.controller.InitView;
import org.apache.deltaspike.core.api.config.view.controller.PostRenderView;
import org.apache.deltaspike.core.api.config.view.controller.PreRenderView;
import org.apache.deltaspike.core.api.config.view.controller.PreViewAction;
import org.jboss.examples.deltaspike.task.management.domain.PageAction;
import org.jboss.examples.deltaspike.task.management.repositories.PageActionRepository;

@Model
public class PagesCallbackHandler implements Serializable {

    @Inject
    private PageActionRepository pageActionRepository;

    @Inject
    private FacesContext facesContext;

    @InitView
    public void initView() {
        pageActionRepository.save(new PageAction(facesContext.getViewRoot().getViewId(), PageEvent.INIT_VIEW));
    }

    @PreRenderView
    public void preRenderView() {
        pageActionRepository.save(new PageAction(facesContext.getViewRoot().getViewId(), PageEvent.PRE_RENDER_VIEW));
    }

    @PreViewAction
    public void preViewAction() {
        pageActionRepository.save(new PageAction(facesContext.getViewRoot().getViewId(), PageEvent.PRE_VIEW_ACTION));
    }

    @PostRenderView
    public void postRenderView() {
        pageActionRepository.save(new PageAction(facesContext.getViewRoot().getViewId(), PageEvent.POST_RENDER_VIEW));
    }

    public List<PageAction> getAllActions() {
        return pageActionRepository.findAllOrdered();
    }
}
