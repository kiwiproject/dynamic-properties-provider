package org.kiwiproject.dynamicproperties.annotation;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("NullChoiceSupplier")
class NullChoiceSupplierTest {

    @Test
    void shouldReturnEmptyUnmodifiableList() {
        assertThat(new NullChoiceSupplier().get())
                .isUnmodifiable()
                .isEmpty();
    }
}
