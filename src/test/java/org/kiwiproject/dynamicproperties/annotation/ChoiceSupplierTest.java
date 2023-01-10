package org.kiwiproject.dynamicproperties.annotation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.kiwiproject.collect.KiwiLists.first;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

@DisplayName("ChoiceSupplier")
class ChoiceSupplierTest {

    @Test
    void shouldSupplyChoices_WhenGivenValidType() {
        var choices = ChoiceSupplier.getChoices(TestChoiceSupplier.class);

        assertThat(choices).hasSize(1);
        assertThat(first(choices).getLabel()).isEqualTo("test choice");
    }

    @ParameterizedTest
    @ValueSource(classes = {SupplierMissingNoArgsConstructor.class, SupplierWithPrivateConstructor.class})
    void shouldThrowIllegalArgumentException_WhenGivenInvalidType(Class<? extends ChoiceSupplier> type) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> ChoiceSupplier.getChoices(type))
                .withMessage("%s is invalid. Does it have a public, no-arguments constructor?", type);
    }

    static class TestChoiceSupplier implements ChoiceSupplier {
        @Override
        public List<Choice> get() {
            return List.of(
                    Choice.builder()
                            .value("1")
                            .label("test choice")
                            .enabled(true)
                            .build()
            );
        }
    }

    static class SupplierMissingNoArgsConstructor implements ChoiceSupplier {

        SupplierMissingNoArgsConstructor(String value) {
            // ignored
        }

        @Override
        public List<Choice> get() {
            return List.of();
        }
    }

    static class SupplierWithPrivateConstructor implements ChoiceSupplier {

        private SupplierWithPrivateConstructor() {
        }

        @Override
        public List<Choice> get() {
            return List.of();
        }
    }
}
