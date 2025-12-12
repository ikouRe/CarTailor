package com.alo.domain;

public final class CategoryImpl implements Category {

    private final String name;

    public CategoryImpl(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
