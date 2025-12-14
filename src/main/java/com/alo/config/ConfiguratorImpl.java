package com.alo.config;

import java.util.Map;
import java.util.Set;

import com.alo.compatibility.CompatibilityChecker;
import com.alo.compatibility.CompatibilityManager;
import com.alo.domain.Category;
import com.alo.domain.part.PartType;

/**
 * Implémentation immuable de {@link Configurator} pour les catégories et
 * variants.
 */
public class ConfiguratorImpl implements Configurator {

    private final Set<Category> categories;
    private final Map<Category, Set<PartType>> variants;
    private final Configuration configuration;
    private final CompatibilityManager manager;

    /**
     * Construit le configurateur à partir des catégories, variants et du
     * manager.
     *
     * @param categories catégories disponibles
     * @param variants variants par catégorie
     * @param manager gestionnaire de compatibilités
     */
    public ConfiguratorImpl(Set<Category> categories,
            Map<Category, Set<PartType>> variants,
            CompatibilityManager manager) {

        this.categories = Set.copyOf(categories);
        this.variants = Map.copyOf(variants);
        this.manager = manager;
        this.configuration = new ConfigurationImpl();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Category> getCategories() {
        return categories;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<PartType> getVariants(Category category) {
        return variants.getOrDefault(category, Set.of());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Configuration getConfiguration() {
        return configuration;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CompatibilityChecker getChecker() {
        return manager;
    }
}
