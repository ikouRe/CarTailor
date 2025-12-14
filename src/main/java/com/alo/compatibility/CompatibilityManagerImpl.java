package com.alo.compatibility;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.alo.domain.part.PartType;

/**
 * Implémentation en mémoire de {@link CompatibilityManager}.
 */
public class CompatibilityManagerImpl implements CompatibilityManager {

    private final Map<PartType, Set<PartType>> incompatibilities = new HashMap<>();
    private final Map<PartType, Set<PartType>> requirements = new HashMap<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<PartType> getIncompatibilities(PartType reference) {
        return incompatibilities.getOrDefault(reference, Set.of());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<PartType> getRequirements(PartType reference) {
        return requirements.getOrDefault(reference, Set.of());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addIncompatibilities(PartType reference, Set<PartType> targets) {
        incompatibilities.computeIfAbsent(reference, x -> new HashSet<>()).addAll(targets);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeIncompatibility(PartType reference, PartType target) {
        incompatibilities.getOrDefault(reference, Set.of()).remove(target);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addRequirements(PartType reference, Set<PartType> targets) {
        requirements.computeIfAbsent(reference, x -> new HashSet<>()).addAll(targets);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeRequirement(PartType reference, PartType target) {
        requirements.getOrDefault(reference, Set.of()).remove(target);
    }
}
