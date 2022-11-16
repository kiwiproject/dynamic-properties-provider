package org.kiwiproject.dynamicproperties;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Define unit of measurement for a field.
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Units {

    /**
     * Set the concrete list of units allowed for this field.
     */
    String[] units();
}
