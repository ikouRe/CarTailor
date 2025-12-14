package com.alo.config;

import java.util.Set;

import com.alo.domain.Category;
import com.alo.domain.part.PartInstance;

/**
 * Représente la sélection courante de pièces pour un véhicule.
 */
public interface Configuration {

    /**
     * Ensemble des pièces sélectionnées.
     *
     * @return sélection courante (copie immuable si possible)
     */
    Set<PartInstance> getSelectedParts();

    /**
     * Sélection pour une catégorie donnée.
     *
     * @param category catégorie cible
     * @return instance sélectionnée ou {@code null} si aucune
     */
    PartInstance getSelectionForCategory(Category category);

    /**
     * Ajoute/remplace la sélection pour la catégorie de l'instance.
     */
    void selectPart(PartInstance instance);

    /**
     * Supprime la sélection pour une catégorie.
     */
    void unselectPart(Category category);

    /**
     * Réinitialise toutes les sélections.
     */
    void clear();
}
