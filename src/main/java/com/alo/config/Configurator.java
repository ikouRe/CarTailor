package com.alo.config;

import java.util.Set;

import com.alo.compatibility.CompatibilityChecker;
import com.alo.domain.Category;
import com.alo.domain.part.PartType;

/**
 * Point d'accès principal au catalogue et à la configuration.
 */
public interface Configurator {

    /**
     * Toutes les catégories disponibles.
     */
    Set<Category> getCategories();

    /**
     * Variants disponibles pour une catégorie donnée.
     */
    Set<PartType> getVariants(Category category);

    /**
     * Configuration associée (mutable).
     */
    Configuration getConfiguration();

    /**
     * Accès au vérificateur de compatibilité.
     */
    CompatibilityChecker getChecker();
}
