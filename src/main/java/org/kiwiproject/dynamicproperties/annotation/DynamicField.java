package org.kiwiproject.dynamicproperties;

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
     * Provide a list of possible values for this field. Useful if field is an enum or restricted to a set of values.
     */
    String[] choices();
}
