package com.alo.domain.part;

import java.util.Objects;

public final class PartInstance {

    private final PartType type;

    public PartInstance(PartType type) {
        this.type = Objects.requireNonNull(type, "PartType cannot be null");
    }

    public PartType getType() {
        return type;
    }

    public String getName() {
        return type.getName();
    }

    @Override
    public String toString() {
        return "PartInstance{" + type.getName() + '}';
    }
}
