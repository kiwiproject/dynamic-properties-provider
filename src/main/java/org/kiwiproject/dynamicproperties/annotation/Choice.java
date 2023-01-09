package org.kiwiproject.dynamicproperties.annotation;

public interface Choice {

    String getValue();

    String getLabel();

    default boolean isEnabled() {
        return true;
    }
}
