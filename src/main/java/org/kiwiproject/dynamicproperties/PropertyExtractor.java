package org.kiwiproject.dynamicproperties;

import static java.util.Arrays.stream;
import static java.util.Collections.emptyList;
import static org.apache.commons.lang3.ArrayUtils.isNotEmpty;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.kiwiproject.reflect.KiwiReflection.nonStaticFieldsInHierarchy;

import lombok.experimental.UtilityClass;
import org.kiwiproject.dynamicproperties.annotation.ChoiceSupplier;
import org.kiwiproject.dynamicproperties.annotation.DynamicField;
import org.kiwiproject.dynamicproperties.annotation.EnumUnit;
import org.kiwiproject.dynamicproperties.annotation.NullChoiceSupplier;
import org.kiwiproject.dynamicproperties.annotation.NullEnum;
import org.kiwiproject.dynamicproperties.annotation.Unit;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@UtilityClass
public class PropertyExtractor {

    public static List<Property> extractPropertiesFromClass(Class<?> clazz) {
        return nonStaticFieldsInHierarchy(clazz).stream()
                .filter(field -> isNotEmpty(field.getAnnotationsByType(DynamicField.class)))
                .map(PropertyExtractor::convertFieldToProperty)
                .toList();
    }

    private static Property convertFieldToProperty(Field field) {
        Class<?> fieldClass = field.getType();

        var dynamicFieldAnnotation = findDynamicFieldAnnotation(field);

        var property = Property.builder()
                .name(field.getName())
                .type(fieldClass.getSimpleName())
                .values(getPossibleValuesForField(fieldClass, dynamicFieldAnnotation))
                .visible(dynamicFieldAnnotation.visible())
                .editable(dynamicFieldAnnotation.editable())
                .label(dynamicFieldAnnotation.label())
                .placeholder(dynamicFieldAnnotation.placeholder())
                .required(dynamicFieldAnnotation.required())
                .sensitive(dynamicFieldAnnotation.sensitive())
                .build();

        addUnitInformationIfNecessary(property, field);

        return property;
    }

    private static List<?> getPossibleValuesForField(Class<?> fieldClass, DynamicField dynamicFieldAnnotation) {
        checkOnlyOneChoiceSupplied(dynamicFieldAnnotation);

        if (isNotEmpty(dynamicFieldAnnotation.choices())) {
            return List.of(dynamicFieldAnnotation.choices());
        }

        if (dynamicFieldAnnotation.choicesFromEnum() != NullEnum.class) {
            return getListFromEnum(dynamicFieldAnnotation.choicesFromEnum());
        }

        if (dynamicFieldAnnotation.choiceSupplier() != NullChoiceSupplier.class) {
            return ChoiceSupplier.getChoices(dynamicFieldAnnotation.choiceSupplier());
        }

        if (fieldClass.isEnum()) {
            return getListFromEnum(fieldClass);
        }

        return emptyList();
    }

    private static void checkOnlyOneChoiceSupplied(DynamicField dynamicFieldAnnotation) {
        int choicesAttributes = 0;
        if (isNotEmpty(dynamicFieldAnnotation.choices())) {
            choicesAttributes += 1;
        }
        if (dynamicFieldAnnotation.choicesFromEnum() != NullEnum.class) {
            choicesAttributes += 1;
        }
        if (dynamicFieldAnnotation.choiceSupplier() != NullChoiceSupplier.class) {
            choicesAttributes += 1;
        }
        if (choicesAttributes > 1) {
            throw new IllegalArgumentException("Only one of 'choices', 'choicesFromEnum', or 'choicesSupplier' may be specified.");
        }
    }

    private static List<String> getListFromEnum(Class<?> enumClass) {
        return stream(enumClass.getEnumConstants())
                .filter(val -> val instanceof Enum)
                .map(val -> ((Enum<?>) val).name())
                .toList();
    }

    private static DynamicField findDynamicFieldAnnotation(Field field) {
        return stream(field.getAnnotations())
                .filter(annotation -> annotation instanceof DynamicField)
                .map(DynamicField.class::cast)
                .findFirst()
                .orElseThrow();
    }

    private static void addUnitInformationIfNecessary(Property property, Field field) {
        var unitAnnotation = findUnitAnnotation(field);
        var enumUnitAnnotation = findEnumUnitAnnotation(field);

        if (unitAnnotation.isPresent() && enumUnitAnnotation.isPresent()) {
            throw new IllegalStateException("@Unit and @EnumUnit are mutually exclusive but both exist.");
        }

        unitAnnotation.ifPresent(annotation -> processUnitAnnotation(annotation, property));
        enumUnitAnnotation.ifPresent(annotation -> processEnumUnitAnnotation(annotation, property));
    }

    private static Optional<Unit> findUnitAnnotation(Field field) {
        return stream(field.getAnnotations())
                .filter(annotation -> annotation instanceof Unit)
                .map(Unit.class::cast)
                .findFirst();
    }

    private static Optional<EnumUnit> findEnumUnitAnnotation(Field field) {
        return stream(field.getAnnotations())
                .filter(annotation -> annotation instanceof EnumUnit)
                .map(EnumUnit.class::cast)
                .findFirst();
    }

    private static void processUnitAnnotation(Unit unitAnnotation, Property property) {
        var unitList = Arrays.asList(unitAnnotation.value());
        property.setUnits(unitList);

        var defaultValue = unitAnnotation.defaultValue();
        if (isNotBlank(defaultValue) && !unitList.contains(defaultValue)) {
            throw new IllegalStateException("Unit default value is not a valid value.");
        }

        property.setDefaultUnit(defaultValue);
    }

    private static void processEnumUnitAnnotation(EnumUnit unitAnnotation, Property property) {
        var unitList = getListFromEnum(unitAnnotation.value());
        property.setUnits(unitList);

        var defaultValue = unitAnnotation.defaultValue();
        if (isNotBlank(defaultValue) && !unitList.contains(defaultValue)) {
            throw new IllegalStateException("EnumUnit default value is not a valid value.");
        }

        property.setDefaultUnit(defaultValue);
    }
}
