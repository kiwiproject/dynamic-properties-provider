package org.kiwiproject.dynamicproperties.annotation;

import java.util.EnumSet;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * This interface indicates that a class can provide a list of {@link Choice}s.
 */
public interface ChoiceSupplier {

    List<Choice> get();

    /**
     * Create {@link Choice}s for the specified <code>Enum</code>.
     *
     * @param enumClass the <code>Enum</code>
     * @param labelFunction a function to provide the label for each value
     *
     * @return a list of choices
     */
    static <E extends Enum<E>> List<Choice> fromEnum(Class<E> enumClass, Function<E, String> labelFunction) {
        return fromEnum(enumClass, labelFunction, enumValue -> true);
    }

    /**
     * Create {@link Choice}s for the specified <code>Enum</code>.
     *
     * @param enumClass the <code>Enum</code>
     * @param labelFunction a function to provide the label for each value
     * @param enabledPredicate a predicate to control if each value should be enabled
     *
     * @return a list of choices
     */
    static <E extends Enum<E>> List<Choice> fromEnum(Class<E> enumClass, Function<E, String> labelFunction, Predicate<E> enabledPredicate) {
        return EnumSet.allOf(enumClass).stream()
                .map(enumValue -> ChoiceImpl.builder()
                        .value(enumValue.name())
                        .label(labelFunction.apply(enumValue))
                        .enabled(enabledPredicate.test(enumValue))
                        .build())
                .collect(Collectors.toList());
    }
}
