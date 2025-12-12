package com.alo.config;

import java.util.*;

import com.alo.domain.Category;
import com.alo.domain.PartType;
import com.alo.compatibility.CompatibilityChecker;

public class ConfigurationImpl implements Configuration {

    private final CompatibilityChecker checker;
    private final Map<Category, PartType> selected = new HashMap<>();

    public ConfigurationImpl(CompatibilityChecker checker) {
        this.checker = checker;
    }

    @Override
    public boolean isComplete() {
        return selected.size() >= 4; // V1 has 4 categories
    }

    @Override
    public boolean isValid() {
        for (PartType p : selected.values()) {
            // incompatibilities
            for (PartType inc : checker.getIncompatibilities(p)) {
                if (selected.containsValue(inc))
                    return false;
            }
            // requirements
            for (PartType req : checker.getRequirements(p)) {
                if (!selected.containsValue(req))
                    return false;
            }
        }
        return true;
    }

    @Override
    public Set<PartType> getSelectedParts() {
        return Set.copyOf(selected.values());
    }

    @Override
    public PartType getSelectionForCategory(Category category) {
        return selected.get(category);
    }

    @Override
    public void selectPart(PartType part) {
        selected.put(part.getCategory(), part);
    }

    @Override
    public void unselectPartType(Category category) {
        selected.remove(category);
    }

    @Override
    public void clear() {
        selected.clear();
    }
}
