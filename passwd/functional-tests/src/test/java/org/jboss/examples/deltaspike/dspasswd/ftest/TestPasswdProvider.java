package org.jboss.examples.deltaspike.dspasswd.ftest;

import java.io.InputStream;
import javax.enterprise.inject.Produces;
import org.apache.deltaspike.core.api.resourceloader.InjectableResource;

public class TestPasswdProvider {
    
    @Produces
    public InputStream getPasswdFile(@InjectableResource(location = "passwd") InputStream is) {
        return is;
    }

}
