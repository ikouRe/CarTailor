package com.alo.service;

import com.alo.config.Configuration;
import com.alo.domain.part.PartInstance;
import com.alo.domain.part.PartType;
import com.alo.domain.part.Property;

/**
 * Service de gestion d'une {@link com.alo.config.Configuration}.
 *
 * <p>
 * Fournit la création d'instances de pièces, l'ajout de pièces à une
 * configuration, l'association de propriétés et la validation de compatibilité
 * globale.</p>
 */
public interface ConfigurationService {

    /**
     * Crée une instance pour un type de pièce.
     *
     * @param type type de pièce à instancier
     * @return nouvelle instance liée au type
     */
    PartInstance createInstance(PartType type);

    /**
     * Ajoute une pièce sélectionnée à la configuration.
     *
     * @param configuration configuration cible
     * @param instance instance de pièce à ajouter
     */
    void addPart(Configuration configuration, PartInstance instance);

    /**
     * Associe une propriété à une instance de pièce.
     *
     * @param instance instance concernée
     * @param property propriété à ajouter
     */
    void addProperty(PartInstance instance, Property property);

    /**
     * Vérifie la validité globale de la configuration au regard des
     * incompatibilités connues.
     *
     * @param configuration configuration à valider
     * @return {@code true} si la configuration est valide, sinon {@code false}
     */
    boolean isValid(Configuration configuration);
}
