package org.kiwiproject.dynamicproperties.annotation;

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
public @interface Unit {

    /**
     * Set the concrete list of units allowed for this field.
     */
    String[] value();

    /**
     * Default value from {@code value} for the field.
     */
    String defaultValue() default "";
}
