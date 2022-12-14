package org.kiwiproject.dynamicproperties.data;

import org.kiwiproject.dynamicproperties.annotation.DynamicField;
import org.kiwiproject.dynamicproperties.annotation.EnumUnit;
import org.kiwiproject.dynamicproperties.annotation.Unit;

@SuppressWarnings("unused") // This is used for tests
public class Student {

    public enum Education {
        HIGH_SCHOOL, BACHELOR, MASTER, PHD
    }

    public enum WeightUnit {
        LBS, G, KG
    }

    private Long id;

    @DynamicField(required = true)
    private String firstName;

    @DynamicField(visible = false)
    private String lastName;

    @DynamicField(editable = false)
    private Double gpa;

    @DynamicField(label = "Currently Enrolled")
    private String school;

    @DynamicField
    private Education educationLevel;

    @DynamicField(choicesFromEnum = Education.class)
    private String highestEducationLevel;

    @DynamicField(choices = {"English", "Geometry", "Physics", "PE", "Art", "Band"})
    private String favoriteSubject;

    @DynamicField
    @Unit(value = {"m", "km"}, defaultValue = "km")
    private Double distanceFromSchool;

    @DynamicField
    @EnumUnit(value = WeightUnit.class, defaultValue = "LBS")
    private Double weight;

    @DynamicField
    @Unit(value = {"ft", "m"})
    private Double height;

    @DynamicField
    @EnumUnit(WeightUnit.class)
    private Double backpackWeight;

    @DynamicField(sensitive = true)
    private String studentPassword;

    @DynamicField(placeholder = "purple")
    private String favoriteColor;
}
