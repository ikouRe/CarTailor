package com.alo.domain.part;

import java.util.Objects;

public final class Property {

    private final String name;
    private final String value;

    public Property(String name, String value) {
        this.name = Objects.requireNonNull(name, "Property name cannot be null");
        this.value = Objects.requireNonNull(value, "Property value cannot be null");
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return name + "=" + value;
    }
}
