package org.kiwiproject.dynamicproperties.annotation;

import static org.kiwiproject.base.KiwiPreconditions.checkArgumentNotNull;
import static org.kiwiproject.base.KiwiStrings.f;

import java.util.List;
import java.util.function.Supplier;

/**
 * Defines the contract for classes that want to supply {@link Choice} objects.
 */
public interface ChoiceSupplier extends Supplier<List<Choice>> {

    /**
     * Returns the choices supplied by the given type of ChoiceSupplier.
     *
     * @param choiceSupplierClass the type of ChoiceSupplier
     * @return a list of choices
     * @throws IllegalArgumentException if the given type is not a valid ChoiceSupplier class
     */
    static List<Choice> getChoices(Class<? extends ChoiceSupplier> choiceSupplierClass) {
        checkArgumentNotNull(choiceSupplierClass);

        try {
            return choiceSupplierClass.getDeclaredConstructor().newInstance().get();
        } catch (Exception e) {
            var message = f("{} is invalid. Does it have a public, no-arguments constructor?", choiceSupplierClass);
            throw new IllegalArgumentException(message);
        }
    }
}
