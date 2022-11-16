package org.kiwiproject.dynamicproperties.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Define unit of measurement for a field based on an enum.
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EnumUnit {

    /**
     * Set the concrete list of units allowed for this field.
     */
    Class<? extends Enum<?>> value();

    /**
     * Default value from {@code value} for the field. Must be a valid enum constant.
     */
    String defaultValue() default "";
}
