package org.kiwiproject.dynamicproperties.annotation;

import lombok.Builder;
import lombok.Getter;

/**
 * {@inheritDoc}
 */
@Builder
@Getter
public class ChoiceImpl implements Choice {
    private final String value;
    private final String label;
    @Builder.Default private final boolean enabled = true;
}
