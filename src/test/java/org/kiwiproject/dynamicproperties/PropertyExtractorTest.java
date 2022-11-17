package org.kiwiproject.dynamicproperties;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.kiwiproject.dynamicproperties.annotation.DynamicField;
import org.kiwiproject.dynamicproperties.annotation.EnumUnit;
import org.kiwiproject.dynamicproperties.annotation.Unit;
import org.kiwiproject.dynamicproperties.data.Student;
import org.kiwiproject.dynamicproperties.data.Student.Education;

import java.util.List;

@DisplayName("PropertyExtractor")
class PropertyExtractorTest {

    @Nested
    class ExtractPropertiesFromClass {

        @Test
        void shouldExtractCorrectProperties() {
            var properties = PropertyExtractor.extractPropertiesFromClass(Student.class);

            var firstNameProperty = Property.builder()
                    .name("firstName")
                    .label("")
                    .type("String")
                    .required(true)
                    .visible(true)
                    .editable(true)
                    .build();

            var lastNameProperty = Property.builder()
                    .name("lastName")
                    .label("")
                    .type("String")
                    .required(false)
                    .visible(false)
                    .editable(true)
                    .build();

            var gpaProperty = Property.builder()
                    .name("gpa")
                    .label("")
                    .type("Double")
                    .required(false)
                    .visible(true)
                    .editable(false)
                    .build();

            var schoolProperty = Property.builder()
                    .name("school")
                    .label("Currently Enrolled")
                    .type("String")
                    .required(false)
                    .visible(true)
                    .editable(true)
                    .build();

            var educationLevelProperty = Property.builder()
                    .name("educationLevel")
                    .label("")
                    .type("Education")
                    .required(false)
                    .visible(true)
                    .editable(true)
                    .values(List.of("HIGH_SCHOOL", "BACHELOR", "MASTER", "PHD"))
                    .build();

            var favoriteSubjectProperty = Property.builder()
                    .name("favoriteSubject")
                    .label("")
                    .type("String")
                    .required(false)
                    .visible(true)
                    .editable(true)
                    .values(List.of("English", "Geometry", "Physics", "PE", "Art", "Band"))
                    .build();

            var distanceFromSchoolProperty = Property.builder()
                    .name("distanceFromSchool")
                    .label("")
                    .type("Double")
                    .required(false)
                    .visible(true)
                    .editable(true)
                    .units(List.of("m", "km"))
                    .defaultUnit("km")
                    .build();

            var weightProperty = Property.builder()
                    .name("weight")
                    .label("")
                    .type("Double")
                    .required(false)
                    .visible(true)
                    .editable(true)
                    .units(List.of("LBS", "G", "KG"))
                    .defaultUnit("LBS")
                    .build();

            var heightProperty = Property.builder()
                    .name("height")
                    .label("")
                    .type("Double")
                    .required(false)
                    .visible(true)
                    .editable(true)
                    .units(List.of("ft", "m"))
                    .defaultUnit("")
                    .build();

            var backpackWeightProperty = Property.builder()
                    .name("backpackWeight")
                    .label("")
                    .type("Double")
                    .required(false)
                    .visible(true)
                    .editable(true)
                    .units(List.of("LBS", "G", "KG"))
                    .defaultUnit("")
                    .build();

            assertThat(properties)
                    .usingRecursiveFieldByFieldElementComparator()
                    .containsExactlyInAnyOrderElementsOf(List.of(
                       firstNameProperty,
                       lastNameProperty,
                       gpaProperty,
                       schoolProperty,
                       educationLevelProperty,
                       favoriteSubjectProperty,
                       distanceFromSchoolProperty,
                       weightProperty,
                       heightProperty,
                       backpackWeightProperty
                    ));
        }

        class TooManyUnits {

            @DynamicField
            @Unit({ "foo" })
            @EnumUnit(Education.class)
            private String unitField;
        }

        @Test
        void shouldThrowExceptionWhenUnitAndEnumUnitAreOnSameField() {
            assertThatThrownBy(() -> PropertyExtractor.extractPropertiesFromClass(TooManyUnits.class))
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessage("@Unit and @EnumUnit are mutually exclusive but both exist.");
        }

        class InvalidUnitDefault {

            @DynamicField
            @Unit(value = { "foo" }, defaultValue = "bar")
            private String unitField;
        }

        @Test
        void shouldThrowExceptionWhenUnitDefaultIsNotInList() {
            assertThatThrownBy(() -> PropertyExtractor.extractPropertiesFromClass(InvalidUnitDefault.class))
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessage("Unit default value is not a valid value.");
        }

        class InvalidEnumUnitDefault {

            @DynamicField
            @EnumUnit(value = Education.class, defaultValue = "KINDERGARTEN")
            private String unitField;
        }

        @Test
        void shouldThrowExceptionWhenEnumUnitDefaultIsNotInList() {
            assertThatThrownBy(() -> PropertyExtractor.extractPropertiesFromClass(InvalidEnumUnitDefault.class))
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessage("EnumUnit default value is not a valid value.");
        }
    }
}
