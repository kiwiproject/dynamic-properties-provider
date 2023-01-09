package org.kiwiproject.dynamicproperties.annotation;

/**
 * This interface describes an <code>option</code> for a <code>select</code> element (or similar UI control).
 * This can be used when you want a different display value than the selected value.
 */
public interface Choice {

    String getValue();

    String getLabel();

    default boolean isEnabled() {
        return true;
    }
}
