package org.kiwiproject.dynamicproperties.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Define unit of measurement for a field based on an enum. This can not be combined with {@link Unit}.
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EnumUnit {

    /**
     * Set the enum class containing units allowed for this field.
     *
     * @return the enum type containing allowed units
     */
    Class<? extends Enum<?>> value();

    /**
     * Default value from {@code value} for the field. Must be a valid enum constant.
     *
     * @return the default enum constant name as a string, defaults to none
     */
    String defaultValue() default "";
}
