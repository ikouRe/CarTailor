package com.alo.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.alo.domain.Category;
import com.alo.domain.part.PartInstance;

/**
 * Implémentation en mémoire de {@link Configuration}.
 */
public class ConfigurationImpl implements Configuration {

    private final Map<Category, PartInstance> selections = new HashMap<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<PartInstance> getSelectedParts() {
        return Set.copyOf(selections.values());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PartInstance getSelectionForCategory(Category category) {
        return selections.get(category);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void selectPart(PartInstance instance) {
        selections.put(instance.getType().getCategory(), instance);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void unselectPart(Category category) {
        selections.remove(category);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clear() {
        selections.clear();
    }
}
