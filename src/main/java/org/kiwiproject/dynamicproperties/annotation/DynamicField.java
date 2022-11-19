package org.kiwiproject.dynamicproperties.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation that indicates a class field is discoverable as dynamic.
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DynamicField {

    /**
     * Informs the caller if this field should be displayed.
     *
     * @return true if this field should be displayed, false otherwise
     */
    boolean visible() default true;

    /**
     * Informs the caller if this field should be editable.
     *
     * @return true if this field is editable, false otherwise
     */
    boolean editable() default true;

    /**
     * Provide a custom label if it needs to be different from the human-readable form of the field.
     *
     * @return the label text, defaults to an empty string
     */
    String label() default "";

    /**
     * Provide a list of possible values for this field. Useful if field is restricted to a set of values.
     *
     * @return the choices that should be available for this field, default is empty array
     */
    String[] choices() default {};

    /**
     * Informs the caller if this field is required.
     *
     * @return true if this field should be displayed, false otherwise
     */
    boolean required() default false;
}
