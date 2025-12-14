package com.alo.domain;

/**
 * Implémentation simple de {@link Category}.
 */
public final class CategoryImpl implements Category {

    private final String name;

    /**
     * Crée une catégorie.
     *
     * @param name nom de la catégorie
     */
    public CategoryImpl(String name) {
        this.name = name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return name;
    }
}
