package org.kiwiproject.dynamicproperties.data;

import org.kiwiproject.dynamicproperties.annotation.Choice;
import org.kiwiproject.dynamicproperties.annotation.ChoiceSupplier;
import org.kiwiproject.dynamicproperties.annotation.DynamicField;

import java.util.List;

public class Course {

    @DynamicField(required = true, choiceSupplier = DepartmentChoiceSupplier.class)
    private Department department;

    @DynamicField(required = true)
    private String number;

    @DynamicField
    private String title;

    public static class DepartmentChoiceSupplier extends ChoiceSupplier {
        @Override
        public List<Choice> get() {
            return ChoiceSupplier.fromEnum(Department.class, d -> String.format("%s - %s", d.getCode(), d.getDescription()));
        }
    }
}
