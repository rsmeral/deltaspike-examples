package org.jboss.examples.deltaspike.mbeantranslator.common;

/**
 * @author Tomas Remes
 */
public enum Language {

    GERMAN("German"),
    FRENCH("French");

    public String getValue() {
        return value;
    }

    private final String value;

    private Language(String value) {
        this.value = value;
    }

}
