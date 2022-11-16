package org.kiwiproject.dynamicproperties;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

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

    @JsonProperty("default")
    private String defaultValue;   

    @Builder.Default
    private List<?> values = new ArrayList<>();

}