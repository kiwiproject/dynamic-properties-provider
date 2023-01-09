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
     * Provide a placeholder value for this field.
     *
     * @return the placeholder value, defaults to an empty string
     */
    String placeholder() default "";

    /**
     * Provide a list of possible values for this field. Useful if field is restricted to a set of values.
     *
     * @return the choices that should be available for this field, default is empty array
     */
    String[] choices() default {};

    /**
     * Provide a list of possible values for this field by using the given Enum as the list. Useful if field is
     * restricted to a set of values.
     *
     * @return the choices that should be available for this field based on the given Enum.
     */
    Class<? extends Enum> choicesFromEnum() default NullEnum.class;

    /**
     * Provide a list of possible values with labels and enabled-ness for this field by using the given ChoiceSupplier.
     * Useful if field is restricted to a set of values.
     *
     * @return the {@link Choice}s that should be available for this field.
     */
    Class<? extends ChoiceSupplier> choiceSupplier() default NullChoiceSupplier.class;

    /**
     * Informs the caller if this field is required.
     *
     * @return true if this field is required, false otherwise
     */
    boolean required() default false;

    /**
     * Informs the caller if this field will have a sensitive value.
     *
     * @return true if this field is required, false otherwise
     */
    boolean sensitive() default false;
}
