package com.alo.domain.part;

import com.alo.domain.Category;

/**
 * Implémentation simple de {@link PartType}.
 */
public final class PartTypeImpl implements PartType {

    private final String name;
    private final Category category;

    /**
     * Crée un type de pièce.
     *
     * @param name nom du type
     * @param category catégorie associée
     */
    public PartTypeImpl(String name, Category category) {
        this.name = name;
        this.category = category;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Category getCategory() {
        return category;
    }
}
