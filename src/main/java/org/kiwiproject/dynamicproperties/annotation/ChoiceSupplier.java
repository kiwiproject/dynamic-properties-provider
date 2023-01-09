package org.kiwiproject.dynamicproperties.annotation;

import java.util.EnumSet;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class ChoiceSupplier {

    public abstract List<Choice> get();

    public static <E extends Enum<E>> List<Choice> fromEnum(Class<E> enumClass, Function<E, String> labelFunction) {
        return fromEnum(enumClass, labelFunction, e -> true);
    }

    public static <E extends Enum<E>> List<Choice> fromEnum(Class<E> enumClass, Function<E, String> labelFunction, Predicate<E> enabledPredicate) {
        return EnumSet.allOf(enumClass).stream()
                .map(enumValue -> ChoiceImpl.builder()
                        .value(enumValue.name())
                        .label(labelFunction.apply(enumValue))
                        .enabled(enabledPredicate.test(enumValue))
                        .build())
                .collect(Collectors.toList());
    }
}
