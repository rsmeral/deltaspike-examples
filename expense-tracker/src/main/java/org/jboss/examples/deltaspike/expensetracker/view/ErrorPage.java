package org.jboss.examples.deltaspike.expensetracker.view;

import org.apache.deltaspike.core.api.config.view.DefaultErrorView;
import org.apache.deltaspike.core.api.exclude.Exclude;
import org.apache.deltaspike.core.api.projectstage.ProjectStage;
import org.apache.deltaspike.jsf.api.config.view.View;

@Exclude(ifProjectStage = ProjectStage.Development.class)
@View(name = "error")
public class ErrorPage extends DefaultErrorView {

}
