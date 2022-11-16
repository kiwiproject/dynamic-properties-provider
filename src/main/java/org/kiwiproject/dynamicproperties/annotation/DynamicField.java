package org.kiwiproject.dynamicproperties.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.kiwiproject.dynamicproperties.NullEnum;

/**
 * Annotation that indicates a class field is discoverable as dynamic.
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DynamicField {

    /**
     * Informs the caller if this field should be displayed.
     */
    boolean visible() default true;

    /**
     * Informs the caller if this field should be ediable.
     */
    boolean editable() default true;

    /**
     * Provide a custom label if it needs to be different than the human readable form of the field.
     */
    String label() default "";

    /**
     * Provide a list of possible values for this field. Useful if field is restricted to a set of values.
     * <p>
     * Note that if this is specified, then {@link #enumChoices()} should not be specified.
     */
    String[] choices() default {};

    /**
     * Provide a list of possible values for this field from an enum source. Useful if field is restricted to a set of values.
     * <p>
     * Note that if this is specified, then {@link #choices()} should not be specified.
     */
    Class<? extends Enum<?>> enumChoices() default NullEnum.class;
}
