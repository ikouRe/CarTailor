package com.alo.service;

import com.alo.compatibility.CompatibilityChecker;
import com.alo.config.Configuration;
import com.alo.domain.part.PartInstance;
import com.alo.domain.part.PartType;
import com.alo.domain.part.Property;

import java.util.HashMap;
import java.util.Map;

public class ConfigurationServiceImpl implements ConfigurationService {

    private final CompatibilityChecker compatibilityChecker;

    // On stocke les propriétés par instance (simple pour V2)
    private final Map<PartInstance, Map<String, Property>> properties = new HashMap<>();

    public ConfigurationServiceImpl(CompatibilityChecker compatibilityChecker) {
        this.compatibilityChecker = compatibilityChecker;
    }

    @Override
    public PartInstance createInstance(PartType type) {
        return new PartInstance(type);
    }

    @Override
    public void addPart(Configuration configuration, PartInstance instance) {
        configuration.selectPart(instance);
        properties.putIfAbsent(instance, new HashMap<>());
    }

    @Override
    public void addProperty(PartInstance instance, Property property) {
        properties
                .computeIfAbsent(instance, k -> new HashMap<>())
                .put(property.getName(), property);
    }

    @Override
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
