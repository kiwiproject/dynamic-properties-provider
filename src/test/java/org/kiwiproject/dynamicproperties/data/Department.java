package org.kiwiproject.dynamicproperties.data;

import lombok.Getter;

@Getter
public enum Department {
    COMPUTER_ENGINEERING("CPE", "Computer Engineering"),
    COMPUTER_SCIENCE("CS", "Computer Science"),
    ENGLISH("EN", "English");

    private final String code;
    private final String description;

    Department(String code, String description) {
        this.code = code;
        this.description = description;
    }
}
