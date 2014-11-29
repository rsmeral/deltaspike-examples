package org.jboss.examples.deltaspike.servletobjects;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import org.jboss.examples.deltaspike.servletobjects.servlet.RequestParam;

@Model
public class Greeter {

    @Inject
    @RequestParam
    private String delay;
    
    private String greeting;

    private String name;

    public String getGreeting() {
        return greeting;
    }

    public void setGreeting(String greeting) {
        this.greeting = greeting;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String slowGreet() throws InterruptedException {
        Thread.sleep(Integer.valueOf(delay));
        String result = "DeltaSpike says '" + greeting + ", " + name + "!'";
        greeting = null;
        name = null;
        return result;
    }

}
