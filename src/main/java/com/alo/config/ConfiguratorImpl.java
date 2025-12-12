package com.alo.config;

import java.util.*;

import com.alo.domain.Category;
import com.alo.domain.part.PartType;
import com.alo.compatibility.CompatibilityChecker;
import com.alo.compatibility.CompatibilityManager;

public class ConfiguratorImpl implements Configurator {

    private final Set<Category> categories;
    private final Map<Category, Set<PartType>> variants;
    private final Configuration configuration;
    private final CompatibilityManager manager;

    public ConfiguratorImpl(Set<Category> categories,
            Map<Category, Set<PartType>> variants,
            CompatibilityManager manager) {

        this.categories = Set.copyOf(categories);
        this.variants = Map.copyOf(variants);
        this.manager = manager;
        this.configuration = new ConfigurationImpl();
    }

    @Override
    public Set<Category> getCategories() {
        return categories;
    }

    @Override
    public Set<PartType> getVariants(Category category) {
        return variants.getOrDefault(category, Set.of());
    }

    @Override
    public Configuration getConfiguration() {
        return configuration;
    }

    @Override
    public CompatibilityChecker getChecker() {
        return manager;
    }
}
