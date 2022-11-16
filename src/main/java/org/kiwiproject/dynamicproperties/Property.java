package org.kiwiproject.dynamicproperties;

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