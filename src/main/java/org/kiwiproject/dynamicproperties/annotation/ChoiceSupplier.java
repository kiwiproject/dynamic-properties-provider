package org.kiwiproject.dynamicproperties.annotation;

import java.util.List;
import java.util.function.Supplier;

/**
 * Defines the contract for classes that want to supply {@link Choice} objects.
 */
public interface ChoiceSupplier extends Supplier<List<Choice>> {
}
