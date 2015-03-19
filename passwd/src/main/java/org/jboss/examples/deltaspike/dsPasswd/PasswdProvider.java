package org.jboss.examples.deltaspike.dsPasswd;

import java.io.InputStream;
import javax.enterprise.inject.Produces;
import org.apache.deltaspike.core.api.exclude.Exclude;
import org.apache.deltaspike.core.api.projectstage.ProjectStage;
import org.apache.deltaspike.core.api.resourceloader.FileResourceProvider;
import org.apache.deltaspike.core.api.resourceloader.InjectableResource;

@Exclude(ifProjectStage = ProjectStage.IntegrationTest.class)
public class PasswdProvider {
    
    @Produces
    public InputStream getPasswdFile(@InjectableResource(location = "/etc/passwd", resourceProvider = FileResourceProvider.class) InputStream file) {
        return file;
    }

}
