package org.kiwiproject.dynamicproperties;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class Property {

    private String name;
    private String label;
    private String type;

    private boolean required;
    private boolean visible;
    private boolean editable;

    private List<String> units;
    private String defaultUnit;

    @Builder.Default
    private List<?> values = new ArrayList<>();

}
