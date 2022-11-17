package org.kiwiproject.dynamicproperties;

import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.ArrayUtils.isNotEmpty;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.kiwiproject.reflect.KiwiReflection.nonStaticFieldsInHierarchy;

import static java.util.Arrays.stream;
import static java.util.Collections.emptyList;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.kiwiproject.dynamicproperties.annotation.DynamicField;
import org.kiwiproject.dynamicproperties.annotation.EnumUnit;
import org.kiwiproject.dynamicproperties.annotation.Unit;
import org.kiwiproject.validation.Required;

import lombok.experimental.UtilityClass;

@UtilityClass
public class PropertyExtractor {

    public static List<Property> extractPropertiesFromClass(Class<?> clazz) {
        return nonStaticFieldsInHierarchy(clazz).stream()
                .filter(field -> isNotEmpty(field.getAnnotationsByType(DynamicField.class)))
                .map(PropertyExtractor::convertFieldToProperty)
                .collect(toList());
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
            .required(isFieldRequired(field))
            .build();

        addUnitInformationIfNecessary(property, field);

        return property;
    }

    private static List<String> getPossibleValuesForField(Class<?> fieldClass, DynamicField dynamicFieldAnnotation) {
        if (fieldClass.isEnum()) {
            return getListFromEnum(fieldClass);
        }

        if (isNotEmpty(dynamicFieldAnnotation.choices())) {
            return Arrays.asList(dynamicFieldAnnotation.choices());
        }

        return emptyList();
    }

    private static List<String> getListFromEnum(Class<?> enumClass) {
        return stream(enumClass.getEnumConstants())
                    .filter(val -> val instanceof Enum)
                    .map(val -> ((Enum<?>) val).name())
                    .collect(toList());
    }

    private static DynamicField findDynamicFieldAnnotation(Field field) {
        return stream(field.getAnnotations())
            .filter(annotation -> annotation instanceof DynamicField)
            .map(annotation -> (DynamicField) annotation)
            .findFirst()
            .orElseThrow();
    }

    private static boolean isFieldRequired(Field field) {
        return stream(field.getAnnotations())
            .anyMatch(annotation -> annotation instanceof Required);
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
            .map(annotation -> (Unit) annotation)
            .findFirst();
    }

    private static Optional<EnumUnit> findEnumUnitAnnotation(Field field) {
        return stream(field.getAnnotations())
            .filter(annotation -> annotation instanceof EnumUnit)
            .map(annotation -> (EnumUnit) annotation)
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