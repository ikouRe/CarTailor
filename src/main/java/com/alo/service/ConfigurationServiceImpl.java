package com.alo.service;

import java.util.HashMap;
import java.util.Map;

import com.alo.compatibility.CompatibilityChecker;
import com.alo.config.Configuration;
import com.alo.domain.part.PartInstance;
import com.alo.domain.part.PartType;
import com.alo.domain.part.Property;

/**
 * Implémentation par défaut de {@link ConfigurationService}.
 *
 * <p>
 * Stocke de façon simple les propriétés associées aux instances de pièces et
 * délègue la validation des incompatibilités au
 * {@link CompatibilityChecker}.</p>
 */
public class ConfigurationServiceImpl implements ConfigurationService {

    private final CompatibilityChecker compatibilityChecker;

    // On stocke les propriétés par instance (simple pour V2)
    private final Map<PartInstance, Map<String, Property>> properties = new HashMap<>();

    /**
     * Crée le service avec le vérificateur de compatibilité fourni.
     *
     * @param compatibilityChecker composant de vérification d'incompatibilités
     */
    public ConfigurationServiceImpl(CompatibilityChecker compatibilityChecker) {
        this.compatibilityChecker = compatibilityChecker;
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public PartInstance createInstance(PartType type) {
        return new PartInstance(type);
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public void addPart(Configuration configuration, PartInstance instance) {
        configuration.selectPart(instance);
        properties.putIfAbsent(instance, new HashMap<>());
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public void addProperty(PartInstance instance, Property property) {
        properties
                .computeIfAbsent(instance, k -> new HashMap<>())
                .put(property.getName(), property);
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public boolean isValid(Configuration configuration) {
        return configuration.getSelectedParts()
                .stream()
                .allMatch(p -> compatibilityChecker
                .getIncompatibilities(p.getType())
                .stream()
                .noneMatch(incompatible -> configuration.getSelectedParts()
                .stream()
                .anyMatch(i -> i.getType().equals(incompatible))));
    }
}
