package org.kiwiproject.dynamicproperties.annotation;

import static java.util.Collections.emptyList;

import java.util.List;

/**
 * Dummy ChoiceSupplier class used as default value for optional attributes of
 * annotations.
 */
public class NullChoiceSupplier implements ChoiceSupplier {

    @Override
    public List<Choice> get() {
        return emptyList();
    }
}
