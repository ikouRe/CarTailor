package com.alo.compatibility;

import java.util.Set;

import com.alo.domain.PartType;

public interface CompatibilityChecker {
    Set<PartType> getIncompatibilities(PartType reference);

    Set<PartType> getRequirements(PartType reference);
}
