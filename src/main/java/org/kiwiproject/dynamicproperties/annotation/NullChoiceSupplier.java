package org.kiwiproject.dynamicproperties.annotation;

import static java.util.Collections.emptyList;

import java.util.List;
import java.util.function.Supplier;

/**
 * Dummy ChoiceSupplier class used as default value for optional attributes of
 * annotations.
 */
public class NullChoiceSupplier implements Supplier<List<Choice>> {

    @Override
    public List<Choice> get() {
        return emptyList();
    }
}
