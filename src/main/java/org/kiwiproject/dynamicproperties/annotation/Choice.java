package org.kiwiproject.dynamicproperties.annotation;

import static java.util.stream.Collectors.toList;

import lombok.Builder;
import lombok.Getter;

import java.util.EnumSet;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * This class describes an <code>option</code> for a <code>select</code> element (or similar UI control)
 * and can be used when you want a different display value than the selected value.
 */
@Builder
@Getter
public class Choice {
    private final String value;
    private final String label;
    @Builder.Default private final boolean enabled = true;

    /**
     * Create {@link Choice}s for the specified <code>Enum</code>.
     *
     * @param enumClass the <code>Enum</code>
     * @param labelFunction a function to provide the label for each <code>Enum</code> value
     *
     * @return a list of {@link Choice}s
     */
    public static <E extends Enum<E>> List<Choice> choicesForEnum(Class<E> enumClass, Function<E, String> labelFunction) {
        return choicesForEnum(enumClass, labelFunction, enumValue -> true);
    }

    /**
     * Create {@link Choice}s for the specified <code>Enum</code>.
     *
     * @param enumClass the <code>Enum</code>
     * @param labelFunction a function to provide the label for each <code>Enum</code> value
     * @param enabledPredicate a predicate to control if each value should be enabled
     *
     * @return a list of {@link Choice}s
     */
    public static <E extends Enum<E>> List<Choice> choicesForEnum(Class<E> enumClass, Function<E, String> labelFunction, Predicate<E> enabledPredicate) {
        return EnumSet.allOf(enumClass).stream()
                .map(enumValue -> Choice.builder()
                        .value(enumValue.name())
                        .label(labelFunction.apply(enumValue))
                        .enabled(enabledPredicate.test(enumValue))
                        .build())
                .collect(toList());
    }
}
