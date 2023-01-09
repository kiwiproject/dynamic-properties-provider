package org.kiwiproject.dynamicproperties.annotation;

import java.util.EnumSet;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class ChoiceSupplier {

    public abstract List<Choice> get();

    public static <E extends Enum<E>> List<Choice> fromEnum(Class<E> enumClass, Function<E, String> labelFunction) {
        return EnumSet.allOf(enumClass).stream()
                .map(enumValue -> ChoiceImpl.builder()
                        .value(enumValue.name())
                        .label(labelFunction.apply(enumValue))
                        .build())
                .collect(Collectors.toList());
    }
}
