package com.alo.compatibility;

import java.util.*;

import com.alo.domain.PartType;

public class CompatibilityManagerImpl implements CompatibilityManager {

    private final Map<PartType, Set<PartType>> incompatibilities = new HashMap<>();
    private final Map<PartType, Set<PartType>> requirements = new HashMap<>();

    @Override
    public Set<PartType> getIncompatibilities(PartType reference) {
        return incompatibilities.getOrDefault(reference, Set.of());
    }

    @Override
    public Set<PartType> getRequirements(PartType reference) {
        return requirements.getOrDefault(reference, Set.of());
    }

    @Override
    public void addIncompatibilities(PartType reference, Set<PartType> targets) {
        incompatibilities.computeIfAbsent(reference, x -> new HashSet<>()).addAll(targets);
    }

    @Override
    public void removeIncompatibility(PartType reference, PartType target) {
        incompatibilities.getOrDefault(reference, Set.of()).remove(target);
    }

    @Override
    public void addRequirements(PartType reference, Set<PartType> targets) {
        requirements.computeIfAbsent(reference, x -> new HashSet<>()).addAll(targets);
    }

    @Override
    public void removeRequirement(PartType reference, PartType target) {
        requirements.getOrDefault(reference, Set.of()).remove(target);
    }
}
