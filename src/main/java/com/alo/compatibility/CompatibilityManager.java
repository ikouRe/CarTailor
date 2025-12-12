package com.alo.compatibility;

import java.util.Set;

import com.alo.domain.part.PartType;

public interface CompatibilityManager extends CompatibilityChecker {

    void addIncompatibilities(PartType reference, Set<PartType> targets);

    void removeIncompatibility(PartType reference, PartType target);

    void addRequirements(PartType reference, Set<PartType> targets);

    void removeRequirement(PartType reference, PartType target);
}
