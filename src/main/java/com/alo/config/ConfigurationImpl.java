package com.alo.config;

import java.util.*;

import com.alo.domain.Category;
import com.alo.domain.part.PartInstance;

public class ConfigurationImpl implements Configuration {

    private final Map<Category, PartInstance> selections = new HashMap<>();

    @Override
    public Set<PartInstance> getSelectedParts() {
        return Set.copyOf(selections.values());
    }

    @Override
    public PartInstance getSelectionForCategory(Category category) {
        return selections.get(category);
    }

    @Override
    public void selectPart(PartInstance instance) {
        selections.put(instance.getType().getCategory(), instance);
    }

    @Override
    public void unselectPart(Category category) {
        selections.remove(category);
    }

    @Override
    public void clear() {
        selections.clear();
    }
}
