package com.alo.compatibility;

import java.util.Set;

import com.alo.domain.part.PartType;

/**
 * Expose les informations de compatibilité pour un type de pièce.
 */
public interface CompatibilityChecker {

    /**
     * Types incompatibles avec le type de référence.
     *
     * @param reference type de pièce considéré
     * @return ensemble (éventuellement vide) des types incompatibles
     */
    Set<PartType> getIncompatibilities(PartType reference);

    /**
     * Types requis pour que le type de référence soit valide.
     *
     * @param reference type de pièce considéré
     * @return ensemble (éventuellement vide) des types requis
     */
    Set<PartType> getRequirements(PartType reference);
}
