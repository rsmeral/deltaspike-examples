package org.jboss.examples.deltaspike.template;

import javax.enterprise.inject.Model;

@Model
public class Greeter {

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

    public String greet() {
        String result = "DeltaSpike says '" + greeting + ", " + name + "!'";
        greeting = null;
        name = null;
        return result;
    }

}
