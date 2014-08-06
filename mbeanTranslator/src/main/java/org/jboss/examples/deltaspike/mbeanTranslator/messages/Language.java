package org.jboss.examples.deltaspike.mbeanTranslator.messages;

/**
 * @author Tomas Remes
 */
public enum Language {

    GERMAN("German"),
    FRENCH("French");

    public String getValue() {
        return value;
    }

    private String value;

    private Language(String value){
        this.value=value;
    }

}
