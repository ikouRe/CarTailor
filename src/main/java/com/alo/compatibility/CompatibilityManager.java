package com.alo.compatibility;

import java.util.Set;

import com.alo.domain.part.PartType;

/**
 * Gestionnaire de compatibilités mutable.
 *
 * <p>
 * Permet d'ajouter et de retirer des incompatibilités et des exigences pour
 * chaque type de pièce.</p>
 */
public interface CompatibilityManager extends CompatibilityChecker {

    /**
     * Ajoute des incompatibilités pour un type de référence.
     */
    void addIncompatibilities(PartType reference, Set<PartType> targets);

    /**
     * Supprime une incompatibilité spécifique.
     */
    void removeIncompatibility(PartType reference, PartType target);

    /**
     * Ajoute des exigences pour un type de référence.
     */
    void addRequirements(PartType reference, Set<PartType> targets);

    /**
     * Supprime une exigence spécifique.
     */
    void removeRequirement(PartType reference, PartType target);
}
