package com.alo.domain;

public final class PartTypeImpl implements PartType {

    private final String name;
    private final Category category;

    public PartTypeImpl(String name, Category category) {
        this.name = name;
        this.category = category;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Category getCategory() {
        return category;
    }
}
