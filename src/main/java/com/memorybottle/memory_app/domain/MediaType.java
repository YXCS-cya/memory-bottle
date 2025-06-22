package com.memorybottle.memory_app.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum MediaType {
    IMAGE,
    VIDEO;

    @JsonCreator
    public static MediaType fromString(String key) {
        return key == null ? null : MediaType.valueOf(key.toUpperCase());
    }

    @JsonValue
    public String getValue() {
        return name();
    }
}
